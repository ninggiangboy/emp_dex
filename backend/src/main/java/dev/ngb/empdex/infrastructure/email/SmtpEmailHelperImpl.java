package dev.ngb.empdex.infrastructure.email;

import dev.ngb.empdex.shared.core.helper.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SmtpEmailHelperImpl implements EmailSender {
    @Override
    public void sendEmail(String to, String subject, String body) {

    }

    @Override
    public void sendEmail(List<String> tos, String subject, String body) {

    }

    @Override
    public void sendEmailTemplate(String to, String subject, String templateName, Map<String, Object> parameters) {

    }

    @Override
    public void sendEmailTemplate(List<String> tos, String subject, String templateName, Map<String, Object> parameters) {

    }
}
