package dev.ngb.empdex.shared.core.helper;

import java.util.List;
import java.util.Map;

public interface EmailSender {
    void sendEmail(String to, String subject, String body);
    void sendEmail(List<String> tos, String subject, String body);
    void sendEmailTemplate(String to, String subject, String templateName, Map<String, Object> parameters);
    void sendEmailTemplate(List<String> tos, String subject, String templateName, Map<String, Object> parameters);
}