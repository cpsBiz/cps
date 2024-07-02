package com.mobcomms.shinhan.config.interceptor;

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
	@Override
	public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor(performanceInterceptor).addPathPatterns("/api/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//"http://localhost:3000","http://127.0.0.1:3000","http://192.168.150.33:3000"
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000","http://127.0.0.1:3000","http://192.168.150.33:3000","http://localhost:8080","http://127.0.0.1:8080","http://localhost","http://127.0.0.1")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
