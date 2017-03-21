package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.Getter;
import ru.reaclicker.dao.LeaderboardDao;
import ru.reaclicker.dao.redisimpl.LeaderboardDaoImpl;
import ru.reaclicker.domain.User;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public class LeaderboardEvent extends SessionHolder implements Event {

    public static final int SIZE_PAGE = 10;

    private LeaderboardDao leaderboardDao;

    public LeaderboardEvent() {
        leaderboardDao = new LeaderboardDaoImpl();
    }

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("leaderboard", Integer.class, (client, page, ackSender) -> {
            int startRank = SIZE_PAGE * page, endRank = startRank + SIZE_PAGE;
            Collection<ResponseUsers> users = leaderboardDao.getRangeUsers(startRank, endRank)
                    .stream()
                    .map(userId -> {
                        User user = userDao.get(userId);
                        return new ResponseUsers(user.getName(), leaderboardDao.getScore(userId));
                    }).collect(Collectors.toList());

            server.getBroadcastOperations().sendEvent("leaderboard", users);
        });
    }

    private class ResponseUsers {

        @Getter String name;
        @Getter double score;

        ResponseUsers(String name, double score) {
            this.name = name;
            this.score = score;
        }
    }
}
