package dev.ngb.empdex.user.internal.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(name = "login_history", schema = "user_module")
public record LoginHistoryEntity(
    @Id @Column("id") Integer id,
    @Column("user_id") UUID userId,
    @Column("login_timestamp") Instant loginTimestamp,
    @Column("ip_address") String ipAddress,
    @Column("user_agent") String userAgent,
    @Column("login_status") String loginStatus,
    @Column("failure_reason") String failureReason
) {
} 