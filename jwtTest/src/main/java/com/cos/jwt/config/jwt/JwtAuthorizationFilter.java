package com.cos.jwt.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.principalDetails;
import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;

//시큐리티 filter가지고 있는데 그 필터중에 basicAuthenticationFilter가 있음
//권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되있음
//만약 권한이 인증이 필요한 주소가 아니라면 이 필어틀 안탐.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private final UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
		System.out.println("basicauthcation필터임");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain);
		System.out.println("dofilter authorization필터임");
		
		String header = request.getHeader("Authorization");
		System.out.println("doFilterInternal :" + header);
		
		if(header == null || header.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		//jwt 토근을 검증해서 정상적인 사용자인지 확인
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(token).getClaim("username").asString();
		if(username != null) {
			User userEntity = userRepository.findByUsername(username);
			
			principalDetails principalDetails = new principalDetails(userEntity);
			//정상적인 로그인 방식과 다르게 강제로 로그인 하는 방법
			//jwt 토근 서명을 통해서 서명이 정상이면 객체를 만들어준다.
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
		
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			chain.doFilter(request, response);
		}
	}
}
