package ru.reaclicker.dao.redisimpl;

import lombok.NonNull;
import ru.reaclicker.dao.UserDao;
import ru.reaclicker.domain.User;

/**
 * Created by Artur Belogur on 16.02.17.
 */
public class UserDaoImpl extends RedisClientHolder implements UserDao {

    private static final String USERS = "users";
    private static final String NAME = "userId";

    @Override
    public long add(@NonNull User user) {
        long id = getIdentifier().incrementAndGet();
        user.setId(id);
        redisClient.getMap(USERS).put(user.getId(), user);
        return id;
    }

    @Override
    public User get(@NonNull long id) {
        return (User) redisClient.getMap(USERS).get(id);
    }

    @Override
    protected String getIdentifierName() {
        return NAME;
    }
}
