package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.domain.User;
import ru.reaclicker.error.ErrorResponse;
import ru.reaclicker.error.ErrorStatus;
import ru.reaclicker.events.core.Event;
import ru.reaclicker.events.core.SessionHolder;
import ru.reaclicker.utility.PasswordHasher;

/**
 * Created by Artur Belogur on 09.03.17.
 */
@Slf4j
public class AuthEvent extends SessionHolder implements Event {

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("register", User.class, (client, userRequest, ackRequest) -> {

            if (userDao.get(userRequest.getName()) != null) {
                client.sendEvent("error", new ErrorResponse("This name already exist", ErrorStatus.NAME_ALREADY_USED));
                return;
            }

            User newUser = new User(userRequest.getName(), PasswordHasher.hashPassword(userRequest.getPassword()));
            userDao.add(newUser);;
        });

        server.addEventListener("login", User.class, (client, userRequest, ackRequest) -> {
            if (userRequest.getName() == null || userRequest.getPassword() == null) {
                client.sendEvent("error", new ErrorResponse("Missed one or more request fields", ErrorStatus.MISS_REQUEST_FIELDS));
            }

            User user = userDao.get(userRequest.getName());
            if (user == null) {
                client.sendEvent("error", new ErrorResponse("Invalid name or password", ErrorStatus.INVALID_USERNAME_OR_PASSWORD));
                return;
            }

            if (!PasswordHasher.checkPassword(userRequest.getPassword(), user.getPassword())) {
                client.sendEvent("error", new ErrorResponse("Invalid name or password", ErrorStatus.INVALID_USERNAME_OR_PASSWORD));
                return;
            }

            loginUsers.put(client.getSessionId(), user);
            server.getBroadcastOperations().sendEvent("number-of-users", loginUsers.size());
        });

        server.addEventListener("check-session", User.class, (client, userRequest, ackRequest) -> {
            User user = loginUsers.get(client.getSessionId());
            if (user == null) {
                client.sendEvent("check-session", false);
            }
            else client.sendEvent("check-session", true);
        });
    }
}
