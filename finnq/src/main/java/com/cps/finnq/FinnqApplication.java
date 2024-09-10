package com.cps.finnq;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(servers = {
        @Server(url = "https://api.commsad.com/finnq"),
        @Server(url = "http://localhost:8080/finnq")
})
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class FinnqApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinnqApplication.class, args);
    }
}
