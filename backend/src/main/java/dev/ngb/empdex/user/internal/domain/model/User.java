package dev.ngb.empdex.user.internal.domain.model;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.domain.AggregateRoot;
import dev.ngb.empdex.user.internal.domain.error.UserErrorCode;
import dev.ngb.empdex.user.internal.domain.event.TooManyFailedLoginsEvent;
import dev.ngb.empdex.user.internal.domain.value_object.Email;
import dev.ngb.empdex.user.internal.domain.value_object.SignInVerificationCode;
import dev.ngb.empdex.user.internal.domain.value_object.Username;
import lombok.Getter;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public class User extends AggregateRoot<UUID> {
    private final Clock clock;
    private final Username username;
    private final Email email;
    private Status status;
    private Instant lastLogin;
    private final List<LoginHistory> lastRecentLoginHistory;

    public enum Status {
        ACTIVE, SUSPENDED
    }

    private User(Username username, Email email, Status status, Instant lastLogin, List<LoginHistory> lastRecentLoginHistory, Clock clock) {
        this.username = username;
        this.email = email;
        this.status = status != null ? status : Status.ACTIVE;
        this.lastLogin = lastLogin;
        this.lastRecentLoginHistory =  new ArrayList<>(lastRecentLoginHistory);
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    public static Result<User> create(String username, String email, Clock clock) {
        try {
            var user = new User(new Username(username), new Email(email), Status.ACTIVE, null, new ArrayList<>(), clock);
            return Result.success(user);
        } catch (IllegalArgumentException e) {
            return Result.failure(UserErrorCode.USER_INVALID_INPUT);
        }
    }

    public static User reconstitute(UUID id, String username, String email, String status, Instant lastLogin, List<LoginHistory> loginHistory, Clock clock) {
        User user = new User(new Username(username), new Email(email), Status.valueOf(status), lastLogin, loginHistory, clock);
        user.id = id;
        return user;
    }

    public Result<Void> suspend() {
        if (isSuspended()) {
            return Result.failure(UserErrorCode.USER_ALREADY_SUSPENDED);
        }
        this.status = Status.SUSPENDED;
        return Result.success();
    }

    public Result<Void> reactivate() {
        if (!isSuspended()) {
            return Result.failure(UserErrorCode.USER_NOT_SUSPENDED);
        }
        this.status = Status.ACTIVE;
        return Result.success();
    }

    public Result<Void> login(String ipAddress, String userAgent) {
        if (isSuspended()) {
            addLoginHistory(LoginHistory.failure(ipAddress, userAgent, UserErrorCode.USER_ALREADY_SUSPENDED.getMessage(), clock));
            if (hasTooManyFailedLoginsWithinLookBackPeriod()) {
                registerDomainEvent(new TooManyFailedLoginsEvent(lastRecentLoginHistory.getLast()));
            }
            return Result.failure(UserErrorCode.USER_ALREADY_SUSPENDED);
        }
        addLoginHistory(LoginHistory.success(ipAddress, userAgent, clock));
        this.lastLogin = clock.instant();
        return Result.success();
    }

    private void addLoginHistory(LoginHistory loginHistory) {
        if (lastRecentLoginHistory.size() >= 10) {
            lastRecentLoginHistory.removeFirst();
        }
        lastRecentLoginHistory.add(loginHistory);
    }

    final int FAILED_LOGIN_THRESHOLD = 3;
    final Duration LOOKBACK_DURATION = Duration.ofSeconds(60);

    private boolean hasTooManyFailedLoginsWithinLookBackPeriod() {
        if (lastRecentLoginHistory.isEmpty()) {
            return false;
        }
        Instant threshold = clock.instant().minus(LOOKBACK_DURATION);
        long failedAttempts = lastRecentLoginHistory.stream()
                .filter(LoginHistory::isFailed)
                .filter(history -> history.getLoginTimestamp().isAfter(threshold))
                .count();

        return failedAttempts >= FAILED_LOGIN_THRESHOLD;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isSuspended() {
        return status == Status.SUSPENDED;
    }

    public List<LoginHistory> getLastRecentLoginHistory() {
        return Collections.unmodifiableList(lastRecentLoginHistory);
    }

    public Result<SignInVerificationCode> generateSignInCode() {
        if (isSuspended()) {
            return Result.failure(UserErrorCode.USER_ALREADY_SUSPENDED);
        }
        var code = SignInVerificationCode.create(id.toString(), Duration.ofMinutes(10));
        return Result.success(code);
    }
}
