package dev.ngb.empdex.user.internal.application.feature.user.command.create_user;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import dev.ngb.empdex.user.internal.domain.service.UserCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateUserHandler implements RequestHandler<CreateUserCommand, UUID> {
    private final UserRepository users;

    @Override
    @Transactional
    public Result<UUID> handle(CreateUserCommand request) {
        Objects.requireNonNull(request);
        var createUserService = new UserCreationService(users::existsByUsernameOrEmail);
        var userCreateResult = createUserService.createUserWithUniqueUsernameAndEmail(request.username(), request.email());
        var user = users.save(userCreateResult.get());
        return Result.success(user.getId());
    }
}