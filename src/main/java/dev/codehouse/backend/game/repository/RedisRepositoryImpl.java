package dev.codehouse.backend.game.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void putBet(String key, String username, String betJson) {
        redisTemplate.opsForHash().put(key, username, betJson);
    }

    @Override
    public String getBet(String key, String username) {
        Object val = redisTemplate.opsForHash().get(key, username);
        return val == null ? null : val.toString();
    }

    @Override
    public Map<String, String> getAllBets(String key) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries == null || entries.isEmpty()) {
            return Collections.emptyMap();
        }
        return entries.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString()
                ));
    }

    @Override
    public void deleteBets(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void addScoreToSortedSet(String key, String member, double score) {
        redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    @Override
    public Set<String> getTopFromSortedSet(String key, int count) {
        Set<String> reversed = redisTemplate.opsForZSet().reverseRange(key, 0, count - 1);
        return reversed == null ? Collections.emptySet() : reversed;
    }

    @Override
    public void deleteSortedSet(String key) {
        redisTemplate.delete(key);
    }
    @Override
    public void putResult(String resultKey, String resultType) {
        redisTemplate.opsForValue().set(resultKey, resultType);
    }

    @Override
    public String getResult(String resultKey) {
        return redisTemplate.opsForValue().get(resultKey);
    }

    @Override
    public void deleteResult(String resultKey) {
        redisTemplate.delete(resultKey);
    }

}