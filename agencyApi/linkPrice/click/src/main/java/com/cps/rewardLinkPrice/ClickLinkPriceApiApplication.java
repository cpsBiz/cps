package com.cps.rewardLinkPrice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(com.cps.agencyService.config.db.DataSourceConfig.class)
@ComponentScan(basePackages = {"com.cps.rewardLinkPrice", "com.cps.agencyService.service", "com.cps.agencyService.repository"})
@EntityScan(basePackages = "com.cps.agencyService.entity")
public class ClickLinkPriceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickLinkPriceApiApplication.class, args);
    }
}
