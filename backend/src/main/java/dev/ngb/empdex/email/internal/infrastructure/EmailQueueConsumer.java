package dev.ngb.empdex.email.internal.infrastructure;

import dev.ngb.empdex.shared.core.helper.EmailSender;
import dev.ngb.empdex.shared.constant.QueueConstants;
import dev.ngb.empdex.email.internal.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailQueueConsumer {

    private final EmailSender emailSender;

    @RabbitListener(queues = QueueConstants.EMAIL_QUEUE)
    public void processEmailMessage(EmailMessage message) {
        try {
            log.info("Processing email message for: {}", message.to());
            
            // Send the actual email using the EmailSender
            emailSender.sendEmailTemplate(message.to(), message.subject(), message.templateName(), message.templateData());
            
            log.info("Email sent successfully to: {}", message.to());
            
        } catch (Exception e) {
            log.error("Failed to send email to: {}, error: {}", message.to(), e.getMessage(), e);
            // In a production environment, you might want to send to a dead letter queue
            // or implement retry logic here
            throw e; // Re-throw to trigger RabbitMQ retry mechanism
        }
    }
} 