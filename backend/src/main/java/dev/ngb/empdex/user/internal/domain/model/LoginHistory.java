package dev.ngb.empdex.user.internal.domain.model;

import dev.ngb.empdex.shared.core.domain.Entity;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter
public class LoginHistory extends Entity<Integer> {
    private final Instant loginTimestamp;
    private final String ipAddress;
    private final String userAgent;
    private final Status loginStatus;
    private final String failureReason;
    private final Clock clock;

    public enum Status {
        SUCCESS, FAILED
    }

    private LoginHistory(Instant loginTimestamp, String ipAddress, String userAgent,
                         Status loginStatus, String failureReason, Clock clock) {
        this.clock = clock != null ? clock : Clock.systemUTC();
        this.loginTimestamp = loginTimestamp != null ? loginTimestamp : this.clock.instant();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.loginStatus = loginStatus;
        this.failureReason = failureReason;
    }

    public static LoginHistory success(String ipAddress, String userAgent, Clock clock) {
        return new LoginHistory(null, ipAddress, userAgent, Status.SUCCESS, null, clock);
    }

    public static LoginHistory failure(String ipAddress, String userAgent, String failureReason, Clock clock) {
        return new LoginHistory(null, ipAddress, userAgent, Status.FAILED, failureReason, clock);
    }

    public boolean isSuccess() {
        return loginStatus == Status.SUCCESS;
    }
    public boolean isFailed() {
        return loginStatus == Status.FAILED;
    }
} 