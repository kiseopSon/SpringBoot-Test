package com.jpa.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.jpa.test.filter.jwtAuthenticationFilter;
import com.jpa.test.filter.jwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private SpringDataTestRepo repo;
	
	@Autowired
	private CorsConfig cors;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("filter Chain인데 들어오냐?");
		return http
						.csrf().disable()
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션,쿠키를 안쓴다는 이야기
						.and()
						.formLogin().disable()
						.httpBasic().disable()//id, pwd를 매번 검증하는방식
						.apply(new CustomDs()) // 커스텀 필터 등록
						.and()
						.authorizeRequests()
						.antMatchers("/login/user/**")
						.access("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
						.antMatchers("/login/manager/**")
						.access("hasRole('MANAGER') or hasRole('ADMIN')")
						.antMatchers("/login/admin/**")
						.access("hasRole('ADMIN')")
						.anyRequest().permitAll()
						.and().build();
					
	}
	
	//커스텀 필터
	public class CustomDs extends AbstractHttpConfigurer<CustomDs, HttpSecurity>{
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager auth = http.getSharedObject(AuthenticationManager.class);
			http
				.addFilter(cors.corsFilter())
				.addFilter(new jwtAuthenticationFilter(auth))
				.addFilter(new jwtAuthorizationFilter(auth, repo));
		}
	}
}
