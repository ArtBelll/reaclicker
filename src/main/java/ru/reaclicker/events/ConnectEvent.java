package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.events.core.Event;

/**
 * Created by Artur Belogur on 18.03.17.
 */
@Slf4j
public class ConnectEvent implements Event {

    @Override
    public void initEvents(SocketIOServer server) {
        server.addConnectListener(client -> {
            log.info("Connect success {}", client.getHandshakeData().getAddress().getAddress());
        });

        server.addDisconnectListener(client -> {
            log.info("User disconnected {}", client.getHandshakeData().getAddress().getAddress());
        });
    }
}
