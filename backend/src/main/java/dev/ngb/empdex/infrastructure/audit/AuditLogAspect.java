package dev.ngb.empdex.infrastructure.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ngb.empdex.shared.core.domain.Entity;
import dev.ngb.empdex.shared.core.helper.IdentifyHelper;
import dev.ngb.empdex.shared.core.helper.RequestIdHelper;
import dev.ngb.empdex.shared.core.helper.TransactionHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLogAspect {
    private final AuditLogRepository auditLogRepository;
    private final HttpServletRequest request;
    private final RequestIdHelper requestIdHelper;
    private final ObjectMapper objectMapper;
    private final TransactionHelper transactionHelper;
    private final IdentifyHelper identifyHelper;

    @Around("""
                @within(dev.ngb.empdex.infrastructure.audit.ApplyAuditLog) &&
                (
                    execution(* dev.ngb.empdex..repository.*Repository.save(..)) ||
                    execution(* dev.ngb.empdex..repository.*Repository.delete(..))
                )
            """)
    public Object saveAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = extractEntity(joinPoint);
        if (entity == null) {
            return joinPoint.proceed();
        }

        Object result = joinPoint.proceed();

        transactionHelper.runAfterCommit(() -> {
            try {
                AuditLogEntity auditLog = buildAuditLog(joinPoint, entity);
                auditLog = auditLogRepository.save(auditLog);
                log.info("Saved audit log: {}", objectMapper.writeValueAsString(auditLog));
            } catch (Exception e) {
                log.warn("Failed to serialize audit log", e);
            }
        });

        return result;
    }

    private Object extractEntity(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return (args.length > 0 && args[0] instanceof Entity<?> entity) ? entity : null;
    }

    private AuditLogEntity buildAuditLog(ProceedingJoinPoint joinPoint, Object entity) {
        AuditLogEntity logEntry = new AuditLogEntity();
        logEntry.setAction(joinPoint.getSignature().getName().toUpperCase());
        logEntry.setEntityName(entity.getClass().getSimpleName());
        logEntry.setEntityId(extractEntityId(entity));
        Optional<String> userId = identifyHelper.getCurrentUserId();
        logEntry.setUserId(userId.orElse(null));
        logEntry.setActorType(userId.isPresent() ? "USER" : "SYSTEM");
        logEntry.setRequestId(requestIdHelper.getRequestId());
        logEntry.setRequestMethod(request.getMethod());
        logEntry.setRequestUri(request.getRequestURI());
        logEntry.setClientIp(request.getRemoteAddr());
        logEntry.setCreatedAt(Instant.now());
        logEntry.setNewValues(serializeToJson(entity));
        return logEntry;
    }

    private String extractEntityId(Object entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object value = idField.get(entity);
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private PGobject serializeToJson(Object obj) {
        try {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(objectMapper.writeValueAsString(obj));
            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to PGobject", e);
        }
    }
}
