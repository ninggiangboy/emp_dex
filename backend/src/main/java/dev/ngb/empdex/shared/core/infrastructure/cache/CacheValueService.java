package dev.ngb.empdex.shared.core.infrastructure.cache;

import java.time.Duration;
import java.util.Optional;

public interface CacheValueService extends BaseCacheService {
    void set(String key, Object value, Duration duration);
    <T> Optional<T> get(String key, Class<T> type);
}
