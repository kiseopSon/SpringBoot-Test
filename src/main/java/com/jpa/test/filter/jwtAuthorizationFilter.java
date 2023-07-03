package com.jpa.test.filter;

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

import com.jpa.test.SpringDataTestRepo;
import com.jpa.test.principalDetailsVO;
import com.jpa.test.testVO;

public class jwtAuthorizationFilter extends BasicAuthenticationFilter{
	private SpringDataTestRepo repo;
	
	public jwtAuthorizationFilter(AuthenticationManager manager, SpringDataTestRepo repo) {
		super(manager);//BasicAuthenticationFilter extends 되어야 가능
		this.repo = repo;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(jwtProperties.HEADER_STRING);
		if(header == null || !header.startsWith(jwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		System.out.println("header : "+header);
		String token = request.getHeader(jwtProperties.HEADER_STRING)
				.replace(jwtProperties.TOKEN_PREFIX, "");
		
		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
		String username = null;
		
		if(username != null) {
			testVO vo = repo.findByUsername(username);
			
			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해 
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장
			principalDetailsVO detailVO = new principalDetailsVO(vo);
			Authentication auth = 
								new UsernamePasswordAuthenticationToken(
																		detailVO,//나중에 컨트롤러에서 di해서 쓸때 사용 편함
																		null,// 패스워드는 모르니깐 null처리, 어차피 지금 인증 안함
																		detailVO.getAuthorities()
																		);
			//강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		chain.doFilter(request, response);
	}
}
