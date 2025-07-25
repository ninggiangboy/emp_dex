package dev.ngb.empdex.shared.core.infrastructure.queue;

public interface QueueService {
    void sendMessage(String queueName, Object message);
}
