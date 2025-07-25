package dev.ngb.empdex.email.internal.dto;

import java.util.Map;

public record EmailMessage(
        String to,
        String subject,
        String templateName,
        Map<String, Object> templateData
) {
}