package dev.ngb.empdex.infrastructure.rabbitmq;

import dev.ngb.empdex.shared.core.infrastructure.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQQueueService implements QueueService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
