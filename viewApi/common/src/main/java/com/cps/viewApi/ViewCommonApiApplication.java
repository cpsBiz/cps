package com.cps.viewApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(com.cps.viewService.config.db.DataSourceConfig.class)
@ComponentScan(basePackages = {"com.cps.viewApi", "com.cps.viewService.service", "com.cps.viewService.repository"})
@EntityScan(basePackages = "com.cps.viewService.entity")
public class ViewCommonApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViewCommonApiApplication.class, args);
    }
}
