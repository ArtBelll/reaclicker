package ru.reaclicker.events.core;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * Created by Artur Belogur on 09.03.17.
 */
public interface Event {
    void initEvents(SocketIOServer server);
}
