package dev.ngb.empdex.user.internal.application.spi;

public interface UserEmailTemplateSender {
    void sendSignInCodeTemplate(String email, String code, long expirationInMinutes);
}
