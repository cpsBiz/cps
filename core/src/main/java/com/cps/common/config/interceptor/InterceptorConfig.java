package com.cps.common.config.interceptor;

import com.cps.common.interceptor.PerformanceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.cps.common"})
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer  {

	private final PerformanceInterceptor performanceInterceptor;
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(performanceInterceptor).addPathPatterns("/api/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(
						"http://localhost:8080",
						"http://127.0.0.1:8080",
						"http://localhost",
						"http://127.0.0.1",
						"http://localhost:8001",
						"http://180.67.204,124",
						"http://180.67.204.119",
						"http://180.67.204.120",
						"http://13.125.179.218:32",
						"http://13.124.188.166:32")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 기본 정적 리소스 경로 설정
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/");
		// 스웨거 경로 설정
		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
	}
}