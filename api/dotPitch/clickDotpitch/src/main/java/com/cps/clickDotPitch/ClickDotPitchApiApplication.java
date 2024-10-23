package com.cps.clickDotPitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(com.cps.common.config.db.DataSourceConfig.class)
@ComponentScan(basePackages = {"com.cps.clickDotPitch", "com.cps.common.service", "com.cps.common.repository", "com.cps.common.utils"})
@EntityScan(basePackages = "com.cps.common.entity")
public class ClickDotPitchApiApplication {
    public static void main(String[] args) {SpringApplication.run(ClickDotPitchApiApplication.class, args);}
}
