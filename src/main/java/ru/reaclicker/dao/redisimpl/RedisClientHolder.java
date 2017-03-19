package ru.reaclicker.dao.redisimpl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import ru.reaclicker.Factory;

/**
 * Created by Artur Belogur on 16.02.17.
 */
@Slf4j
public abstract class RedisClientHolder {

    protected RedissonClient redisClient;

    RedisClientHolder() {
        redisClient = Factory.getRedissonClient();
    }

    protected RAtomicLong identifier = null;

    protected synchronized RAtomicLong getIdentifier() {
        return identifier != null ? identifier : (identifier = redisClient.getAtomicLong(getIdentifierName()));
    }

    protected abstract String getIdentifierName();
}
