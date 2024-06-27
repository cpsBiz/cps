package com.mobcomms.adPanel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "https://api.commsad.com", description = "도메인 설명")})
public class AdPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdPanelApplication.class, args);
    }
}
