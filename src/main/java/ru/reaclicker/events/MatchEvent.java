package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import org.hashids.Hashids;
import ru.reaclicker.domain.User;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

import java.util.HashMap;

/**
 * Created by Artur Belogur on 24.03.17.
 */
public class MatchEvent extends SessionHolder implements Event {

    private Hashids hashids;

    public MatchEvent() {
        hashids = new Hashids();
    }

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("search", Void.class, (client, data, ackSender) -> {
            User user = loginUsers.get(client.getSessionId());
            searchUsers.put(user.getId(), client.getSessionId());

            if (searchUsers.size() > 1) {
                Long enemyId = getEnemyId(user.getId());

                client.sendEvent("start-match", userDao.get(enemyId));
                server.getClient(searchUsers.get(enemyId)).sendEvent("start-match", user);

                searchUsers.remove(user.getId());
                searchUsers.remove(enemyId);

                client.joinRoom(hashids.encode(user.getId(), enemyId));
            }
        });

        server.addEventListener("match-click", Double.class, (client, score, ackSender) -> {
            User user = loginUsers.get(client.getSessionId());
            searchUsers.put(user.getId(), client.getSessionId());


        });
    }

    private Long getEnemyId(Long id) {
        for (Long userId : searchUsers.keySet()) {
            if (!userId.equals(id)) return userId;
        }
        return null;
    }
}
