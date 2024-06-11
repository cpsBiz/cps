package com.mobcomms.cgvSupport.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = {"com.mobcomms.common"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.mobcomms.common.aop.*"))
public class ScanConfig {
}
