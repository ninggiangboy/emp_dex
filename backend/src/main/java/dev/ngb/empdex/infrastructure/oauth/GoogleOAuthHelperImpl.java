package dev.ngb.empdex.infrastructure.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import dev.ngb.empdex.shared.core.helper.GoogleOAuthHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class GoogleOAuthHelperImpl implements GoogleOAuthHelper {
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GoogleOAuth2Properties properties;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String FIELD_ID_TOKEN = "id_token";

    public GoogleOAuthHelperImpl(GoogleOAuth2Properties properties) {
        this.properties = properties;
        this.googleIdTokenVerifier = getGoogleIdTokenVerifier();
    }

    @Override
    public Optional<String> authenticateAndGetUserEmail(String authorizationCode) {
        try {
            var idTokenString = exchangeCodeForIdToken(authorizationCode);
            var idToken = googleIdTokenVerifier.verify(idTokenString);
            if (idToken == null) {
                return Optional.empty();
            }
            var payload = idToken.getPayload();
            return Optional.ofNullable(payload.getEmail());
        } catch (GeneralSecurityException | IOException e) {
            log.error("Failed to verify ID token: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private GoogleIdTokenVerifier getGoogleIdTokenVerifier() {
        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(properties.clientId()))
                .build();
    }

    private String exchangeCodeForIdToken(String code) throws JsonProcessingException {
        var requestBody = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=%s",
                URLEncoder.encode(code, StandardCharsets.UTF_8),
                properties.clientId(),
                properties.clientSecret(),
                properties.redirectUri(),
                GRANT_TYPE);

        var responseBody = restClient
                .post()
                .uri(TOKEN_ENDPOINT)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,(req, res) -> {
                    log.error("Failed to exchange code for token. Status: {}", res.getStatusCode());
                    throw new RuntimeException("Failed to exchange code for token");
                })
                .body(String.class);

        var tokenResponse = objectMapper.readTree(responseBody);

        var idToken = tokenResponse.get(FIELD_ID_TOKEN).asText();
        if (idToken == null || idToken.isEmpty()) {
            return null;
        }
        return idToken;
    }
}