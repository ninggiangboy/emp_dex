package dev.ngb.empdex.user.internal.infrastructure.mapper;

import dev.ngb.empdex.shared.core.domain.EntityMapper;
import dev.ngb.empdex.user.internal.domain.model.User;
import dev.ngb.empdex.user.internal.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

@Component
public class UserEntityMapper implements EntityMapper<User, UserEntity> {
    @Override
    public User toDomain(UserEntity entity) {
        return User.reconstitute(entity.id(), entity.username(), entity.email(), entity.status(), entity.lastLogin(), List.of(), Clock.systemUTC());
    }

    @Override
    public UserEntity toEntity(User domain) {
        return new UserEntity(
                domain.getId(),
                domain.getUsername().value(),
                domain.getEmail().value(),
                domain.getStatus().name(),
                domain.getLastLogin(),
                null
        );
    }
}
