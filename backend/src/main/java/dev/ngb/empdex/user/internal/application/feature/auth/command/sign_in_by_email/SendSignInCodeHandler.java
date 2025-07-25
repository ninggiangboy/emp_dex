package dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_email;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.event.EventPublisher;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.shared.event.SendSignInCodeEmailEvent;
import dev.ngb.empdex.user.internal.application.spi.EmailVerificationRateLimiter;
import dev.ngb.empdex.user.internal.application.spi.VerificationCodeStore;
import dev.ngb.empdex.user.internal.domain.error.AuthErrorCode;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SendSignInCodeHandler implements RequestHandler<SendSignInCodeCommand, Void> {
    private final UserRepository userRepository;
    private final EmailVerificationRateLimiter rateLimiter;
    private final VerificationCodeStore verificationCodeStore;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Result<Void> handle(SendSignInCodeCommand request) {
        // Check rate limit
        if (rateLimiter.isLimited(request.email())) {
            return Result.failure(AuthErrorCode.SPAM_VERIFICATION_REQUIRED);
        }

        var optionalUser = userRepository.findByEmail(request.email());
        if (optionalUser.isEmpty()) {
            return Result.success(); // Do not leak user existence
        }

        var generateCodeResult = optionalUser.get().generateSignInCode();
        if (generateCodeResult.isFailure()) {
            return generateCodeResult.asFailure();
        }

        var code = generateCodeResult.get();

        // Save the verification token
        verificationCodeStore.saveCode(code.userId(), code.value(), code.expirationInMillis());

        // Publish the event to send the email
        eventPublisher.publish(new SendSignInCodeEmailEvent(request.email(), code.value(), code.expirationInMillis()));
        // Set cooldown
        final long emailVerificationRateLimitCooldownSeconds = 60; // Example cooldown duration
        rateLimiter.setCooldown(request.email(), Duration.ofSeconds(emailVerificationRateLimitCooldownSeconds));

        return Result.success();
    }
}
