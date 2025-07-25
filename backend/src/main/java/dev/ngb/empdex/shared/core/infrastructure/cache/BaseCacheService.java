package dev.ngb.empdex.shared.core.infrastructure.cache;

public interface BaseCacheService {
    void delete(String key);
    boolean hasKey(String key);
}
