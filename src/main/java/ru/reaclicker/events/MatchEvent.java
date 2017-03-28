package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import ru.reaclicker.domain.User;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

/**
 * Created by Artur Belogur on 24.03.17.
 */
@Slf4j
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
                SocketIOClient enemyClient = server.getClient(searchUsers.get(enemyId));

                client.sendEvent("start-match", userDao.get(enemyId));
                enemyClient.sendEvent("start-match", user);

                searchUsers.remove(user.getId());
                searchUsers.remove(enemyId);

                String room = hashids.encode(user.getId(), enemyId);
                client.joinRoom(room);
                enemyClient.joinRoom(room);
            }
        });

        server.addEventListener("match-click", Integer.class, (client, score, ackSender) -> {
            String roomName = client.getAllRooms()
                    .stream()
                    .filter(room -> !room.equals(""))
                    .findFirst()
                    .orElse(null);
            if (roomName == null) {
                return;
            }

            server.getRoomOperations(roomName)
                    .getClients()
                    .forEach(user -> {
                        if (user.getSessionId() != client.getSessionId()) {
                            user.sendEvent("enemy-click", score);
                        }
                    });
        });
    }

    private Long getEnemyId(Long id) {
        for (Long userId : searchUsers.keySet()) {
            if (!userId.equals(id)) return userId;
        }
        return null;
    }
}
