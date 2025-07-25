package dev.ngb.empdex.email;

import dev.ngb.empdex.email.event.SendEmailEvent;
import dev.ngb.empdex.email.internal.application.EmailService;
import dev.ngb.empdex.email.internal.dto.EmailMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@RabbitListenerTest
class EmailEventIntegrationTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void shouldPublishEmailEvent() {
        // Given
        String email = "test@example.com";
        String subject = "Test Subject";
        String templateName = "test-template";
        Map<String, Object> templateData = Map.of("key", "value");

        // When
        emailService.sendEmail(email, subject, templateName, templateData);

        // Then
        verify(rabbitTemplate).convertAndSend(
                eq("email.exchange"),
                eq("email.send"),
                any(EmailMessage.class)
        );
    }

    @Test
    void shouldPublishWelcomeEmailEvent() {
        // Given
        String email = "welcome@example.com";
        String userName = "TestUser";

        // When
        emailService.sendWelcomeEmail(email, userName);

        // Then
        verify(rabbitTemplate).convertAndSend(
                eq("email.exchange"),
                eq("email.send"),
                any(EmailMessage.class)
        );
    }

    @Test
    void shouldPublishSignInCodeEmailEvent() {
        // Given
        String email = "signin@example.com";
        String code = "123456";
        long expirationInMinutes = 10;

        // When
        emailService.sendSignInCodeEmail(email, code, expirationInMinutes);

        // Then
        verify(rabbitTemplate).convertAndSend(
                eq("email.exchange"),
                eq("email.send"),
                any(EmailMessage.class)
        );
    }
} 