package dev.ngb.empdex.user.internal.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(name = "users", schema = "user_module")
public record UserEntity(@Id @Column("id") UUID id, @Column("username") String username, @Column("email") String email,
                         @Column("status") String status, @Column("last_login") Instant lastLogin,
                         @Column("deleted_at") Instant deletedAt) {
}
