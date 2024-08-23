package com.mobcomms.hanapay;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@OpenAPIDefinition(servers = {
        @Server(url = "https://api.commsad.com/hanapay"),
        @Server(url = "http://localhost:8080/hanapay")
})
@SpringBootApplication
@EnableJpaAuditing
public class HanaPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanaPayApplication.class, args);
    }
}
