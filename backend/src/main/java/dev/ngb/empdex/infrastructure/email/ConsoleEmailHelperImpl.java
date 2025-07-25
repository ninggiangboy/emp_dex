package dev.ngb.empdex.infrastructure.email;

import dev.ngb.empdex.shared.core.helper.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Profile("dev")
@Component
@Slf4j
public class ConsoleEmailHelperImpl implements EmailSender {
    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("Sending email to: {}, subject: {}, body: {}", to, subject, body);
    }

    @Override
    public void sendEmail(List<String> tos, String subject, String body) {
        log.info("Sending email to: {}, subject: {}, body: {}", tos, subject, body);
    }

    @Override
    public void sendEmailTemplate(String to, String subject, String templateName, Map<String, Object> parameters) {
        log.info("Sending email to: {}, subject: {}, template: {}, parameters: {}", to, subject, templateName, parameters);
    }

    @Override
    public void sendEmailTemplate(List<String> tos, String subject, String templateName, Map<String, Object> parameters) {
        log.info("Sending email to: {}, subject: {}, template: {}, parameters: {}", tos, subject, templateName, parameters);
    }
}
