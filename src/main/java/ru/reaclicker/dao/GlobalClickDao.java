package ru.reaclicker.dao;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public interface GlobalClickDao {

    long get();

    void increase(int score);
}
