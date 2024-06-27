package com.mobcomms.adPanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AdPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdPanelApplication.class, args);
    }
}
