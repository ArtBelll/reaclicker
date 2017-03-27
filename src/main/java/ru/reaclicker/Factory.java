package ru.reaclicker;

import org.redisson.api.RedissonClient;
import ru.reaclicker.domain.User;

import java.util.*;

/**
 * Created by Artur Belogur on 09.03.17.
 */
public class Factory {

    private RedissonClient redissonClient;

    private Map<UUID, User> loginUsers = new HashMap<>();

    private TreeMap<Long, UUID> searchUsers = new TreeMap<>();

    private static class LazyHolder {
        private static final Factory INSTANCE = new Factory();
    }

    public static Factory getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static RedissonClient getRedissonClient() {
        return getInstance().redissonClient;
    }

    public static void setRedissonClient(RedissonClient rc) {
        getInstance().redissonClient = rc;
    }

    public static Map<UUID, User> getLoginUsers() {
        return getInstance().loginUsers;
    }

    public static TreeMap<Long, UUID> getSearchUsers() {
        return getInstance().searchUsers;
    }
}
