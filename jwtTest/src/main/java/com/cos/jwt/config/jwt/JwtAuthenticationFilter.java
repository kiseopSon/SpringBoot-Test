package com.cos.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cos.jwt.config.auth.principalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

// /login요청해서 username, password 전송하면 동작해야하는데 현재는 안함
//해당 extends를 작동시키려면 AuthenticationManager을 전달해주면됨
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	//login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication 로그인 실행중");
		//1. username, password 받아서
	try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			while ((input = br.readLine()) != null) {
//				System.out.println(input);
//				
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(request.getInputStream(), User.class);
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		UsernamePasswordAuthenticationToken authToken  =
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		
		System.out.println("authToken :" + authToken);
		//principalDetailsService의 loaduserbyUsername함수가 실행됨
		Authentication authentication =
				authenticationManager.authenticate(authToken);
		System.out.println("authentication :" + authentication);
		
		principalDetails lDetails = (principalDetails) authentication.getPrincipal();
		
		System.out.println("lDetails :" + lDetails);
		
		return authentication;//정상적으로 로그인이 되었다면, 해당객체가 리턴되면서 session영역에 저장이 되어있음.
		//인증이 성공하면 successfulAuthentication함수가 실행됨
		//단지 권한문제 떄문에 세션에 담음
//			}
		} catch (Exception e) {
		System.out.println("attemptAuthentication 에러 " + e.getMessage());
		e.printStackTrace();
		}
		
		
		//2. 정상인지 로그인 시도를 해본다. -> principalDetailsService 호출
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
}
