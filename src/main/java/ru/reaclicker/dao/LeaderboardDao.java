package ru.reaclicker.dao;

import java.util.Collection;

/**
 * Created by Artur Belogur on 19.03.17.
 */
public interface LeaderboardDao {

    void add(long userId, double score);

    void increaseScore(long userId, double score);

    Integer getRank(long userId);

    Double getScore(long userId);

    Collection<Long> getRangeUsers(int startRank, int endRank);
}