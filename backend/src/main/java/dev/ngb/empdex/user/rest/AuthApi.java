package dev.ngb.empdex.user.rest;

import dev.ngb.empdex.shared.core.rest.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping("/oauth2/google")
    Response<String> loginWithGoogle(@RequestBody Map<String, String> body, HttpServletRequest req);
}
