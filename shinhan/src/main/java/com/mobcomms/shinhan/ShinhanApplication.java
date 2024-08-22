package com.mobcomms.shinhan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@OpenAPIDefinition(servers = {
        @Server(url = "https://api.commsad.com/shinhan/ponint-banner", description = "에이닉 공통도메인"),
        @Server(url = "http://localhost:8080/shinhan/ponint-banner", description = "로컬 개발 환경")
})
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ShinhanApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShinhanApplication.class, args);
    }
}
