package dev.ngb.empdex.user.internal.domain.service;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.user.internal.domain.error.UserErrorCode;
import dev.ngb.empdex.user.internal.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.util.function.BiPredicate;

@RequiredArgsConstructor
public class UserCreationService {
    private final BiPredicate<String, String> usernameExistsPredicate;
    private final Clock clock;

    public UserCreationService(BiPredicate<String, String> usernameExistsPredicate) {
        this(usernameExistsPredicate, Clock.systemUTC());
    }

    public Result<User> createUserWithUniqueUsernameAndEmail(String username, String email) {
        if (usernameExistsPredicate.test(username, email)) {
            return Result.failure(UserErrorCode.USERNAME_OR_EMAIL_EXISTS);
        }

        return User.create(username, email, clock);
    }
}
