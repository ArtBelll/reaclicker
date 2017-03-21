package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.dao.RelationsNameIdDao;
import ru.reaclicker.dao.redisimpl.RelationsNameIdDaoImpl;
import ru.reaclicker.error.ErrorResponse;
import ru.reaclicker.error.ErrorStatus;
import ru.reaclicker.events.core.SessionHolder;
import ru.reaclicker.utility.PasswordHasher;
import ru.reaclicker.dao.UserDao;
import ru.reaclicker.dao.redisimpl.UserDaoImpl;
import ru.reaclicker.domain.User;
import ru.reaclicker.events.core.Event;

/**
 * Created by Artur Belogur on 09.03.17.
 */
@Slf4j
public class AuthEvent extends SessionHolder implements Event {

    private RelationsNameIdDao relationsNameIdDao;

    public AuthEvent() {
        relationsNameIdDao = new RelationsNameIdDaoImpl();
    }

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("register", User.class, (client, userRequest, ackRequest) -> {

            if (relationsNameIdDao.getId(userRequest.getName()) != null) {
                client.sendEvent("error", new ErrorResponse("This name already exist", ErrorStatus.NAME_ALREADY_USED));
                return;
            }

            User newUser = new User(userRequest.getName(), PasswordHasher.hashPassword(userRequest.getPassword()));
            userDao.add(newUser);

            relationsNameIdDao.add(newUser.getName(), newUser.getId());
        });

        server.addEventListener("login", User.class, (client, userRequest, ackRequest) -> {
            if (userRequest.getName() == null || userRequest.getPassword() == null) {
                client.sendEvent("error", new ErrorResponse("Missed one or more request fields", ErrorStatus.MISS_REQUEST_FIELDS));
            }

            Long userId = relationsNameIdDao.getId(userRequest.getName());
            if (userId == null) {
                client.sendEvent("error", new ErrorResponse("Invalid name or password", ErrorStatus.INVALID_USERNAME_OR_PASSWORD));
                return;
            }

            User user = userDao.get(userId);
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
