package dev.ngb.empdex.user.internal.application.spi;

public interface VerificationCodeStore {
    void saveCode(String email, String code, long expirationMillis);
    boolean isCodeValid(String email, String code);
    void revokeCode(String email);
}
