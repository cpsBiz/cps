package com.mobcomms.adPanel.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("https://api.commsad.com") // 허용할 Origin 설정
				.allowedMethods(ALLOWED_METHOD_NAMES.split(",")) // 허용할 HTTP 메서드 설정
				.allowedHeaders("*") // 허용할 헤더 설정
				.allowCredentials(true) // 쿠키 인증 정보 허용 여부 설정
				.maxAge(3600); // Preflight 요청 결과를 캐싱할 시간 설정
	}
}
