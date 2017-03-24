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
    private static final String RELATION_NAME_ID = "relationsNameId";

    @Override
    public long add(@NonNull User user) {
        long id = getIdentifier().incrementAndGet();
        user.setId(id);
        redisClient.getMap(USERS).put(user.getId(), user);
        redisClient.getMap(RELATION_NAME_ID).put(user.getName(), id);
        return id;
    }

    @Override
    public User get(long id) {
        return (User) redisClient.getMap(USERS).get(id);
    }

    @Override
    public User get(@NonNull String name) {
        Long id = (Long) redisClient.getMap(RELATION_NAME_ID).get(name);
        return id == null ? null : get(id);
    }

    @Override
    protected String getIdentifierName() {
        return NAME;
    }
}
