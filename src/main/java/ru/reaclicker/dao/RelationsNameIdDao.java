package ru.reaclicker.dao;

import lombok.NonNull;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public interface RelationsNameIdDao {

    void add(@NonNull String name, long id);

    Long getId(String name);
}
