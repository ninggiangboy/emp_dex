package dev.ngb.empdex.user.internal.application.feature.user.command.reactivate_user;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.user.internal.domain.error.UserErrorCode;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactivateUserHandler implements RequestHandler<ReactivateUserCommand, Void> {
    private final UserRepository users;

    @Override
    public Result<Void> handle(ReactivateUserCommand request) {
        var user = users.findById(request.userId());
        if (user.isEmpty()) {
            return Result.failure(UserErrorCode.USER_NOT_FOUND);
        }
        var result = user.get().reactivate();
        if (result.isFailure()) {
            return result.asFailure();
        }
        users.save(user.get());
        return Result.success();
    }
}
