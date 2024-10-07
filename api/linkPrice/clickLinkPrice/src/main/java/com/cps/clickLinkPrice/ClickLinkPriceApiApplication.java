package com.cps.clickLinkPrice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(com.cps.cpsService.config.db.DataSourceConfig.class)
@ComponentScan(basePackages = {"com.cps.clickLinkPrice", "com.cps.cpsService.service", "com.cps.cpsService.repository", "com.cps.common.utils"})
@EntityScan(basePackages = "com.cps.cpsService.entity")
public class ClickLinkPriceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickLinkPriceApiApplication.class, args);
    }
}
