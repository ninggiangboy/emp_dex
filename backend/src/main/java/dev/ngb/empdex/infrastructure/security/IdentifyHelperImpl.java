package dev.ngb.empdex.infrastructure.security;

import dev.ngb.empdex.shared.core.helper.IdentifyHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class IdentifyHelperImpl implements IdentifyHelper {
    @Override
    public Optional<String> getCurrentUserId() {
        return Optional.of(UUID.randomUUID().toString());
    }
}
