package com.mobcomms.adPanel.config.interceptor;

import com.mobcomms.common.interceptor.PerformanceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
}
