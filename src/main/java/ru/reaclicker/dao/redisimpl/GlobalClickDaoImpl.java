package ru.reaclicker.dao.redisimpl;

import org.redisson.api.RAtomicLong;
import ru.reaclicker.dao.GlobalClickDao;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public class GlobalClickDaoImpl extends RedisClientHolder implements GlobalClickDao {

    public static final String GLOBAL = "global";

    @Override
    public void increase(int score) {
        redisClient.getAtomicLong("global");
    }

    @Override
    protected String getIdentifierName() {
        return null;
    }
}
