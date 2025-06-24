package dev.codehouse.backend.game.repository;

import java.util.Map;
import java.util.Set;

public interface RedisRepository {

    void putBet(String key, String username, String betJson);

    String getBet(String key, String username);

    Map<String, String> getAllBets(String key);

    void deleteBets(String key);

    void addScoreToSortedSet(String key, String member, double score);

    Set<String> getTopFromSortedSet(String key, int count);

    void deleteSortedSet(String key);

    void putResult(String resultKey, String resultType);

    String getResult(String resultKey);

    void deleteResult(String resultKey);

}
