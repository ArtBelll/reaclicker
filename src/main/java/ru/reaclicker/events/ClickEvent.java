package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import ru.reaclicker.dao.GlobalClickDao;
import ru.reaclicker.dao.LeaderboardDao;
import ru.reaclicker.dao.UserDao;
import ru.reaclicker.dao.redisimpl.GlobalClickDaoImpl;
import ru.reaclicker.dao.redisimpl.LeaderboardDaoImpl;
import ru.reaclicker.dao.redisimpl.UserDaoImpl;
import ru.reaclicker.domain.User;
import ru.reaclicker.error.ErrorResponse;
import ru.reaclicker.error.ErrorStatus;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public class ClickEvent extends SessionHolder implements Event {

    private LeaderboardDao leaderboardDao;
    private GlobalClickDao globalClickDao;

    public ClickEvent() {
        leaderboardDao = new LeaderboardDaoImpl();
        globalClickDao = new GlobalClickDaoImpl();
    }

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("click-global", Double.class, (client, score, ackSender) -> {
            User user = loginUsers.get(client.getSessionId());
            if (user == null) {
                client.sendEvent("error", new ErrorResponse("User don't have session", ErrorStatus.USER_DONT_HAVE_SESSION));
                return;
            }

            if (leaderboardDao.getRank(user.getId()) == null) {
                leaderboardDao.add(user.getId(), score);
            }
            else {
                leaderboardDao.increaseScore(user.getId(), score);
            }

            globalClickDao.increase(score.intValue());

            server.getBroadcastOperations().sendEvent("leaderboard-update", true);
            server.getBroadcastOperations().sendEvent("global", globalClickDao.get());
        });

        server.addEventListener("get-global", Double.class, (client, data, ackSender) -> {
            client.sendEvent("global", globalClickDao.get());
        });
    }
}
