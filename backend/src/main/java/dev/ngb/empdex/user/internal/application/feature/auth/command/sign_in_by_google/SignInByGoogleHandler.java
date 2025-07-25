package dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_google;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.helper.GoogleOAuthHelper;
import dev.ngb.empdex.shared.core.helper.JwtHelper;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import dev.ngb.empdex.user.internal.domain.error.AuthErrorCode;
import dev.ngb.empdex.user.internal.domain.error.UserErrorCode;
import dev.ngb.empdex.user.internal.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SignInByGoogleHandler implements RequestHandler<SignInByGoogleCommand, String> {
    private final GoogleOAuthHelper googleOAuthHelper;
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Result<String> handle(SignInByGoogleCommand request) {
        var email = googleOAuthHelper.authenticateAndGetUserEmail(request.authorizationCode());
        if (email.isEmpty()) {
            return Result.failure(AuthErrorCode.GOOGLE_OAUTH_ERROR);
        }
        var user = userRepository.findByEmail(email.get());
        if (user.isEmpty()) {
            return Result.failure(UserErrorCode.USER_NOT_FOUND);
        }
        var loginResult = user.get().login(request.ipAddress(), request.userAgent());
        if (loginResult.isFailure()) {
            return loginResult.asFailure();
        }
        userRepository.save(user.get());
        var token = jwtHelper.generateToken(email.get());
        return Result.success(token);
    }
}