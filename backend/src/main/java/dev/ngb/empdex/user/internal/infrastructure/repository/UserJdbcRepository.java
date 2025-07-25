package dev.ngb.empdex.user.internal.infrastructure.repository;

import dev.ngb.empdex.user.internal.infrastructure.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJdbcRepository extends CrudRepository<UserEntity, UUID> {
    @NotNull
    @Query("SELECT * FROM user_module.users WHERE deleted_at IS NULL")
    List<UserEntity> findAll();

    @NotNull
    @Query("SELECT * FROM user_module.users WHERE id = :id AND deleted_at IS NULL")
    Optional<UserEntity> findById(@NotNull UUID id);

    @Modifying
    @Query("UPDATE user_module.users SET deleted_at = NOW() WHERE id = :id")
    void softDeleteById(UUID id);

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByEmail(String email);
}
