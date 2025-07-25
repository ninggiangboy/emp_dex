package dev.ngb.empdex.infrastructure.audit;

import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLogEntity, Integer> {
}
