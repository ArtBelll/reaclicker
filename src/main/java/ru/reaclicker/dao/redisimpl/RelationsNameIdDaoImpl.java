package ru.reaclicker.dao.redisimpl;

import lombok.NonNull;
import ru.reaclicker.dao.RelationsNameIdDao;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public class RelationsNameIdDaoImpl extends RedisClientHolder implements RelationsNameIdDao {

    private static final String RELATION_ID_NAME = "relationsIdName";

    @Override
    public void add(@NonNull String name, long id) {
        redisClient.getMap(RELATION_ID_NAME).put(name, id);
    }

    @Override
    public Long getId(String name) {
        return (Long) redisClient.getMap(RELATION_ID_NAME).get(name);
    }

    @Override
    protected String getIdentifierName() {
        return null;
    }
}
