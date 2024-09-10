package com.cps.agencyService.config.interceptor;

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
						"http://localhost:3000",
						"http://127.0.0.1:3000",
						"http://192.168.150.33:3000",
						"http://localhost:8080",
						"http://127.0.0.1:8080",
						"http://localhost",
						"http://127.0.0.1",
						"http://localhost:8001",
						"https://fgw.finnq.com",
						"https://sfgw.finnq.com",
						"http://10.251.1.161:8090",
						"http://10.251.1.162:8090",
						"https://api.commsad.com")
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
