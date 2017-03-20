package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import ru.reaclicker.dao.LeaderboardDao;
import ru.reaclicker.dao.redisimpl.LeaderboardDaoImpl;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

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
            int startRank = page, endRank = page + SIZE_PAGE;
            leaderboardDao.getRangeUsers(startRank, endRank);
        });
    }
}
