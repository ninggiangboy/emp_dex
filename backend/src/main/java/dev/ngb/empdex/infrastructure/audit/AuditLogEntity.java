package dev.ngb.empdex.infrastructure.audit;

import lombok.Getter;
import lombok.Setter;
import org.postgresql.util.PGobject;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name = "audit_logs")
@Getter
@Setter
public class AuditLogEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("action")
    private String action;

    @Column("entity_name")
    private String entityName;

    @Column("entity_id")
    private String entityId;

    @Column("user_id")
    private String userId;

    @Column("actor_type")
    private String actorType;

    @Column("request_id")
    private String requestId;

    @Column("request_method")
    private String requestMethod;

    @Column("request_uri")
    private String requestUri;

    @Column("client_ip")
    private String clientIp;

    @Column("created_at")
    private Instant createdAt;

    @Column("new_values")
    private PGobject newValues;

    @Column("details")
    private String details;
}
