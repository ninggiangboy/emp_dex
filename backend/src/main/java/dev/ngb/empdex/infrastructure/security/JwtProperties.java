package dev.ngb.empdex.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, long expiration) {
    public long getExpirationInMillis() {
        return expiration * 1000; // convert seconds to milliseconds
    }
}