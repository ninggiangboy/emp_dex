package dev.ngb.empdex.user.internal.infrastructure.token;

import dev.ngb.empdex.shared.core.infrastructure.cache.CacheValueService;
import dev.ngb.empdex.user.internal.application.spi.VerificationCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CacheVerificationCodeStore implements VerificationCodeStore {
    private static final String EMAIL_VERIFICATION_KEY_PATTERN = "email-verification-code:%s";

    private final CacheValueService cacheValueService;

    @Override
    public void saveCode(String email, String code, long expirationMillis) {
        var tokenKey = String.format(EMAIL_VERIFICATION_KEY_PATTERN, email);
        cacheValueService.set(tokenKey, code, Duration.ofMillis(expirationMillis));
    }

    @Override
    public boolean isCodeValid(String email, String code) {
        var tokenKey = String.format(EMAIL_VERIFICATION_KEY_PATTERN, email);
        var storedCode = cacheValueService.get(tokenKey, String.class);
        // Check if the provided code matches the stored code
        return storedCode.map(s -> s.equals(code)).orElse(false);
    }

    @Override
    public void revokeCode(String email) {
        var tokenKey = String.format(EMAIL_VERIFICATION_KEY_PATTERN, email);
        cacheValueService.delete(tokenKey);
    }
}
