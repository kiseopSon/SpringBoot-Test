package com.jpa.test.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.test.loginReqeustDTOtest;
import com.jpa.test.principalDetailsVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager manager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("jwtAuthenticationFilter 진입");
		
		ObjectMapper om = new ObjectMapper();
		loginReqeustDTOtest dto = null;
		try {
			dto = om.readValue(request.getInputStream(), loginReqeustDTOtest.class);
		} catch (Exception e) {
			System.out.println("jwtAuthenticationFilter.attemptAuthentication exception : " + e.getMessage());
		}
		
		System.out.println("jwtAuthenticationFilter" + dto);
		
		//유저네임패스워드 토큰 생성
		UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(
															dto.getUsername(),
															dto.getPassword()
															);
		System.out.println("JwtAuthenticationFilter : 토큰생성완료");
		
		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		
		Authentication auth = manager.authenticate(token);
		
		principalDetailsVO vo = (principalDetailsVO) auth.getPrincipal();
		System.out.println("Authentication : "+ vo.getUser().getUsername());
		return auth;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		principalDetailsVO vo = (principalDetailsVO) authResult.getPrincipal();
		
		String jwtToken = JWT.create()
									.withSubject(vo.getUsername())
									.withExpiresAt(new Date(System.currentTimeMillis()+jwtProperties.EXPIRATION_TIME))
									.withClaim("username", vo.getUser().getUsername())
									.sign(Algorithm.HMAC512(jwtProperties.SECRET));
		response.addHeader(jwtProperties.HEADER_STRING, jwtProperties.TOKEN_PREFIX+jwtToken);
	}
	
}
