package dev.ngb.empdex.infrastructure.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.oauth2")
public record GoogleOAuth2Properties(String clientId, String clientSecret, String redirectUri) {
}
