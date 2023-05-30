package com.cos.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource sourse = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); //내서버가 응답을 할때 json을 자바스크립트에서 처리할건지 여부
		config.addAllowedOrigin("*"); // 모든 응답
		config.addAllowedHeader("*"); //모든 해더 응답
		config.addAllowedMethod("*"); // post,get 등등 응답
		sourse.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(sourse);
	}
}
