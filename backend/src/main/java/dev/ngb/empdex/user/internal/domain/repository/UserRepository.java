package dev.ngb.empdex.user.internal.domain.repository;

import dev.ngb.empdex.shared.core.domain.BaseRepository;
import dev.ngb.empdex.user.internal.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<User> findByEmail(String email);
}