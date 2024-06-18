package com.mobcomms.raising;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RaisingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaisingApplication.class, args);
    }
}
