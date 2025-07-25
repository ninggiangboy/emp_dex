package dev.ngb.empdex.infrastructure.redis;

import dev.ngb.empdex.shared.core.infrastructure.cache.CacheValueService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisValueService extends RedisCacheService implements CacheValueService {
    public RedisValueService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(type.cast(value));
    }
}
