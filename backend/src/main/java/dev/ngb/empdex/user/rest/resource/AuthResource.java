package dev.ngb.empdex.user.rest.resource;

import dev.ngb.empdex.shared.core.mediator.Mediator;
import dev.ngb.empdex.shared.core.rest.Response;
import dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_google.SignInByGoogleCommand;
import dev.ngb.empdex.user.rest.AuthApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthResource implements AuthApi {
    private final Mediator mediator;

    @Override
    public Response<String> loginWithGoogle(Map<String, String> body, HttpServletRequest req) {
        var code = body.get("code");
        var ipAddress = req.getRemoteAddr();
        var userAgent = req.getHeader("User-Agent");
        var result = mediator.send(new SignInByGoogleCommand(code, ipAddress, userAgent));
        return Response.create(result);
    }
}
