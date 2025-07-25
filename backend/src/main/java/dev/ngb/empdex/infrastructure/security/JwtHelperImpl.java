package dev.ngb.empdex.infrastructure.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.ngb.empdex.shared.core.helper.JwtHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHelperImpl implements JwtHelper {
    private final JwtProperties properties;
    private final JWSSigner signer;

    public JwtHelperImpl(JwtProperties properties) {
        this.properties = properties;
        this.signer = createSigner();
    }

    @Override
    public String generateToken(String email) {
        try {
            var now = System.currentTimeMillis();
            var claims = new JWTClaimsSet.Builder().subject(email).issueTime(new Date(now)).expirationTime(new Date(now + properties.getExpirationInMillis())).build();
            var signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    private JWSSigner createSigner() {
        try {
            return new MACSigner(properties.secret().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWT signer", e);
        }
    }
} 