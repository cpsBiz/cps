package com.cps.common.config.interceptor;

import com.cps.common.filter.HttpLoggingFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    @Bean
    public FilterRegistrationBean<HttpLoggingFilter> onceFilter(){
        FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setUrlPatterns(List.of("/*"));
        registrationBean.setFilter(new HttpLoggingFilter());
        registrationBean.setOrder(3);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ERROR);
        return registrationBean;
    }
}