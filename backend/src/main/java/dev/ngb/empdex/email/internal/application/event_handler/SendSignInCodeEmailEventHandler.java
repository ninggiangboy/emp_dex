package dev.ngb.empdex.email.internal.application.event_handler;

import dev.ngb.empdex.email.internal.dto.EmailMessage;
import dev.ngb.empdex.shared.core.event.EventHandler;
import dev.ngb.empdex.shared.core.infrastructure.queue.QueueService;
import dev.ngb.empdex.shared.event.SendSignInCodeEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendSignInCodeEmailEventHandler implements EventHandler<SendSignInCodeEmailEvent> {
    private final QueueService queueService;

    @Override
    @ApplicationModuleListener
    public void handle(SendSignInCodeEmailEvent event) {
        final String template = "user/send-sign-in-code-template";
        final String subject = "Sign in code for your Empdex account";

        Map<String, Object> templateData = Map.of(
                "email", event.email(),
                "code", event.code(),
                "expirationInMinutes", event.expirationInMinutes()
        );

        queueService.sendMessage("email", new EmailMessage(event.email(), subject, template, templateData));
    }
}
