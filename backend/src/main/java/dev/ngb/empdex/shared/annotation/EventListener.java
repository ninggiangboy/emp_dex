package dev.ngb.empdex.shared.annotation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener
public @interface EventListener {
    @AliasFor(annotation = RabbitListener.class, attribute = "queues")
    String[] queues() default {};

    @AliasFor(annotation = RabbitListener.class, attribute = "id")
    String id() default "";

    @AliasFor(annotation = RabbitListener.class, attribute = "concurrency")
    String concurrency() default "";
}

