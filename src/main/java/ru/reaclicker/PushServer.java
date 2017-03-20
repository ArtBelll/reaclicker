package ru.reaclicker;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.events.ClickEvent;
import ru.reaclicker.events.ConnectEvent;
import ru.reaclicker.events.AuthEvent;
import ru.reaclicker.events.LeaderboardEvent;
import ru.reaclicker.events.core.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 09.03.17.
 */
@Slf4j
public class PushServer {

    private SocketIOServer server;

    PushServer(Configuration conf) {
        server = new SocketIOServer(conf);
        initEvents();
    }

    void start() {
        server.start();
    }

    private void initEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new AuthEvent());
        events.add(new ConnectEvent());
        events.add(new ClickEvent());
        events.add(new LeaderboardEvent());
        events.forEach(e -> e.initEvents(server));
    }
}
