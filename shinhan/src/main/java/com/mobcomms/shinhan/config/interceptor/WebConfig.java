package com.mobcomms.shinhan.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Created by enliple
 * Create Date : 2024-07-03
 * Class 설명, method
 * UpdateDate : 2024-07-03, 업데이트 내용
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DomainInterceptor domainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(domainInterceptor);
    }
}