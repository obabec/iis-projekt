package isu.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IisProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(IisProjektApplication.class, args);
    }

}
