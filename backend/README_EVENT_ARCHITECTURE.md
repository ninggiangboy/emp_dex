# Event-Driven Email Architecture

This document describes the event-driven email architecture implemented in the Empdex backend.

## Architecture Overview

```
Module A (User, Order, Auth, …)
    |
    | -- [In-process event] → ApplicationEventPublisher.publish(SendEmailEvent)
    |
Module Email
    |
    | -- [@EventListener or listener class] → capture SendEmailEvent
        |
        | → convert into RabbitMQ message
        | → push into queue: email.send.queue
    |
Worker (RabbitMQ Consumer)
    |
    | -- receive from email.send.queue → send the actual email
```

## Components

### 1. Events

- **`SendEmailEvent`**: Generic email event that can be published by any module
- **`SendSignInCodeEmailEvent`**: Specific event for sign-in code emails (existing)

### 2. Event Handlers

- **`SendEmailEventHandler`**: Converts `SendEmailEvent` to RabbitMQ messages
- **`SendSignInCodeEmailEventHandler`**: Direct email sending (existing, can be deprecated)

### 3. RabbitMQ Configuration

- **Queue**: `email.send.queue`
- **Exchange**: `email.exchange`
- **Routing Key**: `email.send`
- **Consumer**: `EmailQueueConsumer` processes messages from the queue

### 4. Services

- **`EmailService`**: Provides methods to trigger email sending through events

## How to Use

### From Other Modules

1. **Inject the EmailService**:

```java
@Service
public class UserService {
    private final EmailService emailService;

    public void sendWelcomeEmail(String email, String userName) {
        emailService.sendWelcomeEmail(email, userName);
    }
}
```

2. **Or publish events directly**:

```java
@Service
public class OrderService {
    private final EventPublisher eventPublisher;

    public void sendOrderConfirmation(String email, String orderId) {
        SendEmailEvent event = new SendEmailEvent(
            email,
            "Order Confirmation",
            "order/confirmation-template",
            Map.of("orderId", orderId)
        );
        eventPublisher.publish(event);
    }
}
```

### REST API Endpoints

- `POST /api/email/send` - Send generic email
- `POST /api/email/send-signin-code` - Send sign-in code email
- `POST /api/email/send-welcome` - Send welcome email

## Configuration

### RabbitMQ Settings

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        retry:
          enabled: true
          initial-interval: 1000
          max-attempts: 3
          multiplier: 1.0
          max-interval: 10000
```

### Docker Compose

```bash
cd backend
docker-compose up -d
```

This will start:

- PostgreSQL on port 5432
- Redis on port 6379
- RabbitMQ on port 5672
- RabbitMQ Management UI on port 15672 (http://localhost:15672)

## Benefits

1. **Decoupling**: Email sending is completely decoupled from business logic
2. **Scalability**: Email processing can be scaled independently
3. **Reliability**: RabbitMQ provides message persistence and retry mechanisms
4. **Monitoring**: RabbitMQ Management UI provides queue monitoring
5. **Asynchronous**: Email sending doesn't block the main application flow

## Error Handling

- Failed email sends trigger RabbitMQ retry mechanism
- Logs are captured at each step for debugging
- In production, consider implementing dead letter queues for failed messages

## Migration from Direct Email Sending

The existing `SendSignInCodeEmailEventHandler` still works but can be deprecated in favor of the new event-driven approach. To migrate:

1. Replace direct `EmailSender` usage with `EmailService` calls
2. Remove the old event handler
3. Update tests to verify event publishing instead of direct email sending

## Testing

```bash
# Start services
docker-compose up -d

# Test email sending
curl -X POST http://localhost:8080/api/email/send-welcome \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","userName":"Test User"}'

# Check RabbitMQ Management UI
open http://localhost:15672
```
