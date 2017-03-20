package ru.reaclicker.dao;

import lombok.NonNull;
import ru.reaclicker.domain.Leaderboard;

import java.util.Collection;
import java.util.List;

/**
 * Created by Artur Belogur on 19.03.17.
 */
public interface LeaderboardDao {

    void add(long userId, double score);

    void increaseScore(long userId, double score);

    int getRank(long userId);

    Collection<Long> getRangeUsers(int startRank, int endRank);
}