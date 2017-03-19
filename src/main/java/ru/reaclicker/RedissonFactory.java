package ru.reaclicker;

import org.redisson.api.RedissonClient;

/**
 * Created by Artur Belogur on 09.03.17.
 */
public class RedissonFactory {

    private RedissonClient redissonClient;

    private static class LazyHolder {
        private static final RedissonFactory INSTANCE = new RedissonFactory();
    }

    public static RedissonFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static RedissonClient getRedissonClient() {
        return getInstance().redissonClient;
    }

    public static void setRedissonClient(RedissonClient rc) {
        getInstance().redissonClient = rc;
    }
}
