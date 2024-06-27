package com.mobcomms.adPanel.config.interceptor;

import com.mobcomms.common.interceptor.PerformanceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.mobcomms.common"})
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer  {

	private final PerformanceInterceptor performanceInterceptor;
	public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(performanceInterceptor).addPathPatterns("/api/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*") // 허용할 Origin 설정
				.allowedMethods(ALLOWED_METHOD_NAMES.split(",")) // 허용할 HTTP 메서드 설정
				.allowedHeaders("*") // 허용할 헤더 설정
				.allowCredentials(true) // 쿠키 인증 정보 허용 여부 설정
				.maxAge(3600); // Preflight 요청 결과를 캐싱할 시간 설정
	}
}
