package dev.ngb.empdex.infrastructure.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class TaskExecutorConfig {
    @Bean
    public Executor taskExecutor() {
        var delegate = Executors.newCachedThreadPool();
        return new DelegatingSecurityContextExecutorService(new MdcPropagatingExecutorService(delegate));
    }
}
