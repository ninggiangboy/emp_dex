package dev.ngb.empdex.infrastructure.oauth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GoogleOAuth2Properties.class)
public class OAuthConfig {
}
