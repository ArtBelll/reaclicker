package ru.reaclicker.events.core;

import ru.reaclicker.Factory;
import ru.reaclicker.dao.UserDao;
import ru.reaclicker.dao.redisimpl.UserDaoImpl;
import ru.reaclicker.domain.User;

import java.util.*;

/**
 * Created by Artur Belogur on 19.03.17.
 */
public abstract class SessionHolder {

    protected UserDao userDao;

    protected Map<UUID, User> loginUsers;

    protected TreeMap<Long, UUID> searchUsers;

    protected SessionHolder() {
        userDao = new UserDaoImpl();
        loginUsers = Factory.getLoginUsers();
        searchUsers = Factory.getSearchUsers();
    }
}
