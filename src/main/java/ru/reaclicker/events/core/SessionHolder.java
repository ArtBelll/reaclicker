package ru.reaclicker.events.core;

import ru.reaclicker.Factory;
import ru.reaclicker.domain.User;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Artur Belogur on 19.03.17.
 */
public abstract class SessionHolder {

    protected Map<UUID, User> loginUsers;

    protected SessionHolder() {
        loginUsers = Factory.getLoginUsers();
    }
}
