package dev.ngb.empdex.infrastructure.redis;

import dev.ngb.empdex.shared.core.infrastructure.cache.BaseCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class RedisCacheService implements BaseCacheService {
    protected final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
