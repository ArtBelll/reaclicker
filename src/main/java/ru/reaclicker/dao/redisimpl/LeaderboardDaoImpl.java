package ru.reaclicker.dao.redisimpl;

import org.redisson.api.RScoredSortedSet;
import ru.reaclicker.dao.LeaderboardDao;

import java.util.Collection;

/**
 * Created by Artur Belogur on 20.03.17.
 */
public class LeaderboardDaoImpl extends RedisClientHolder implements LeaderboardDao {

    private static final String LEADERBOARD = "leaderboard";

    @Override
    public void add(long userId, double score) {
        redisClient.getScoredSortedSet(LEADERBOARD).add(score, userId);
    }

    @Override
    public void increaseScore(long userId, double score) {
        redisClient.getScoredSortedSet(LEADERBOARD).addScore(userId, score);
    }

    @Override
    public Integer getRank(long userId) {
        return redisClient.getScoredSortedSet(LEADERBOARD).rank(userId);
    }

    @Override
    public Double getScore(long userId) {
        return redisClient.getScoredSortedSet(LEADERBOARD).getScore(userId);
    }

    @Override
    public Collection<Long> getRangeUsers(int startRank, int endRank) {
        RScoredSortedSet<Long> leaderboard = redisClient.getScoredSortedSet(LEADERBOARD);
        return leaderboard.valueRange(startRank, endRank);
    }

    @Override
    protected String getIdentifierName() {
        return null;
    }
}
