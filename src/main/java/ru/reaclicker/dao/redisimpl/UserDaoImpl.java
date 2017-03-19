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
        redisClient.getMap(USERS).put(user.getName(), user);
        return id;
    }

    @Override
    public User get(@NonNull String name) {
        return (User) redisClient.getMap(USERS).get(name);
    }

    @Override
    public void update(@NonNull User user) {
        redisClient.getList(USERS).add(user);
    }

    @Override
    protected String getIdentifierName() {
        return NAME;
    }
}
