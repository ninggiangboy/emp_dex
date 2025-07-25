package dev.ngb.empdex.user.internal.infrastructure.email;

import dev.ngb.empdex.shared.core.helper.EmailSender;
import dev.ngb.empdex.user.internal.application.spi.UserEmailTemplateSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserEmailTemplateSenderImpl implements UserEmailTemplateSender {
    private final EmailSender emailSender;

    @Override
    public void sendSignInCodeTemplate(String email, String code, long expirationInMinutes) {
        final String template = "user/send-sign-in-code-template";
        final String subject = "Sign in code for your Empdex account";

        Map<String, Object> templateData = Map.of(
                "email", email,
                "code", code,
                "expirationInMinutes", expirationInMinutes
        );

        emailSender.sendEmailTemplate(email, template, subject, templateData);
    }
}
