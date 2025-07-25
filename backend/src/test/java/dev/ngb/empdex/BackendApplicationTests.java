package dev.ngb.empdex;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        var modules = ApplicationModules.of(BackendApplication.class).verify();
        modules.forEach(System.out::println);
    }

}
