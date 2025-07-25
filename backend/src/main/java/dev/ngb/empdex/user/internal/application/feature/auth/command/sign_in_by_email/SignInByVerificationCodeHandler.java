package dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_email;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.helper.JwtHelper;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.user.internal.application.spi.VerificationCodeStore;
import dev.ngb.empdex.user.internal.domain.error.AuthErrorCode;
import dev.ngb.empdex.user.internal.domain.error.UserErrorCode;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SignInByVerificationCodeHandler implements RequestHandler<SignInByVerificationCodeCommand, String> {
    private final UserRepository userRepository;
    private final VerificationCodeStore verificationCodeStore;
    private final JwtHelper jwtHelper;

    @Override
    @Transactional
    public Result<String> handle(SignInByVerificationCodeCommand request) {
        var isValidCode = verificationCodeStore.isCodeValid(request.email(), request.code());
        if (!isValidCode) {
            return Result.failure(AuthErrorCode.INVALID_VERIFICATION_CODE);
        }
        var user = userRepository.findByEmail(request.email());
        if (user.isEmpty()) {
            return Result.failure(UserErrorCode.USER_NOT_FOUND);
        }
        var loginResult = user.get().login(request.ipAddress(), request.userAgent());
        if (loginResult.isFailure()) {
            return loginResult.asFailure();
        }
        verificationCodeStore.revokeCode(request.email());
        userRepository.save(user.get());
        var token = jwtHelper.generateToken(request.email());
        return Result.success(token);
    }
}
