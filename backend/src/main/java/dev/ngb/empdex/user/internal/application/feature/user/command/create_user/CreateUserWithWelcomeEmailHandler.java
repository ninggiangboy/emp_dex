package dev.ngb.empdex.user.internal.application.feature.user.command.create_user;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.email.internal.application.EmailService;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import dev.ngb.empdex.user.internal.domain.service.UserCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserWithWelcomeEmailHandler implements RequestHandler<CreateUserCommand, UUID> {
    
    private final UserRepository users;
    private final EmailService emailService;

    @Override
    @Transactional
    public Result<UUID> handle(CreateUserCommand request) {
        Objects.requireNonNull(request);
        
        log.info("Creating user with email: {}", request.email());
        
        var createUserService = new UserCreationService(users::existsByUsernameOrEmail);
        var userCreateResult = createUserService.createUserWithUniqueUsernameAndEmail(request.username(), request.email());
        
        if (userCreateResult.isSuccess()) {
            var user = users.save(userCreateResult.get());
            
            // Send welcome email asynchronously through the event system
            try {
                emailService.sendWelcomeEmail(request.email(), request.username());
                log.info("Welcome email event published for user: {}", user.getId());
            } catch (Exception e) {
                log.error("Failed to publish welcome email event for user: {}", user.getId(), e);
                // Don't fail user creation if email fails - it's not critical
            }
            
            return Result.success(user.getId());
        } else {
            return Result.failure(userCreateResult.getError());
        }
    }
} 