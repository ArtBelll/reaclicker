package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;

/**
 * Created by Artur Belogur on 18.03.17.
 */
@Slf4j
public class ConnectEvent extends SessionHolder implements Event {

    @Override
    public void initEvents(SocketIOServer server) {
        server.addConnectListener(client -> {
            log.info("Connect success {}", client.getHandshakeData().getAddress().getAddress());
        });

        server.addDisconnectListener(client -> {
            loginUsers.remove(client.getSessionId());
            server.getBroadcastOperations().sendEvent("number-of-users", loginUsers.size());
            log.info("User disconnected {}", client.getHandshakeData().getAddress().getAddress());
        });
    }
}
