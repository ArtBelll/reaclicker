package ru.reaclicker.events;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import ru.reaclicker.error.ErrorResponse;
import ru.reaclicker.error.ErrorStatus;
import ru.reaclicker.utility.PasswordHasher;
import ru.reaclicker.dao.UserDao;
import ru.reaclicker.dao.redisimpl.UserDaoImpl;
import ru.reaclicker.domain.User;
import ru.reaclicker.events.core.Event;

/**
 * Created by Artur Belogur on 09.03.17.
 */
@Slf4j
public class UserEvent implements Event {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void initEvents(SocketIOServer server) {
        server.addEventListener("register", User.class, (socketIOClient, user, ackRequest) -> {
            if (userDao.get(user.getName()) != null) {
                socketIOClient.sendEvent("error", new ErrorResponse("This name already exist", ErrorStatus.NAME_ALREADY_USED));
                return;
            }
            User newUser = new User(user.getName(), PasswordHasher.hashPassword(user.getPassword()));
            userDao.add(newUser);
        });
    }
}
