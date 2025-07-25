package dev.ngb.empdex;

import dev.ngb.empdex.user.internal.infrastructure.entity.UserEntity;
import dev.ngb.empdex.user.internal.infrastructure.repository.UserJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
