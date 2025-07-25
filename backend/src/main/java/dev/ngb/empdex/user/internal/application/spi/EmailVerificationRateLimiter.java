package dev.ngb.empdex.user.internal.application.spi;

import java.time.Duration;

public interface EmailVerificationRateLimiter {
    boolean isLimited(String email);

    void setCooldown(String email, Duration cooldownDuration);
}
