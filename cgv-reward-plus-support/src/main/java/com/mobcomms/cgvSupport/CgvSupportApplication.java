package com.mobcomms.cgvSupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CgvSupportApplication {
    public static void main(String[] args) {
        SpringApplication.run(CgvSupportApplication.class, args);
    }
}
