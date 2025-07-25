package dev.ngb.empdex.user.internal.infrastructure.limiter;

import dev.ngb.empdex.shared.core.infrastructure.cache.CacheValueService;
import dev.ngb.empdex.user.internal.application.spi.EmailVerificationRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisEmailVerificationRateLimiter implements EmailVerificationRateLimiter {
    public static final String EMAIL_VERIFICATION_RATE_LIMIT_KEY_PATTERN = "email-verification-rate-limit:%s";

    private final CacheValueService cacheValueService;

    @Override
    public boolean isLimited(String email) {
        String rateLimitKey = String.format(EMAIL_VERIFICATION_RATE_LIMIT_KEY_PATTERN, email);
        return cacheValueService.hasKey(rateLimitKey);
    }

    @Override
    public void setCooldown(String email, Duration cooldownDuration) {
        String rateLimitKey = String.format(EMAIL_VERIFICATION_RATE_LIMIT_KEY_PATTERN, email);
        cacheValueService.set(
                rateLimitKey,
                "1", // The value is not important, we just need the key to exist
                cooldownDuration
        );
    }
}
