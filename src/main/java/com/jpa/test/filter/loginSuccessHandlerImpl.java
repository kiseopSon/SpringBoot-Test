package com.jpa.test.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jpa.test.principalDetailsVO;

//세션방식
//SecurityFilterChain filterChain(HttpSecurity http) -> http.formLogin().successHandler에 파라미터로 넣음
//인터섭터 구간이라 사전에 다른곳에서 주입받은게 없다면 new 인스턴스해서 사용해야 함.
public class loginSuccessHandler implements AuthenticationSuccessHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		principalDetailsVO vo = (principalDetailsVO) authentication.getPrincipal();
		
		Collection<GrantedAuthority> authList = (Collection<GrantedAuthority>) vo.getAuthorities();
		
		Iterator<GrantedAuthority> iter_authList = authli.iterator();
		String path = request.getContextPath();//현재 기본 패스
		String url = path+"/main";//메인페이지
		
		while (list_it.hasNext()) {
			GrantedAuthority authority = list_it.next();
			System.out.println("success has next : " + authority.getAuthority());//role 이름
			
			//if조건달아서 setAttribute로 'ADMIN', 'MANAGER' 등등 내맘대로 지정
			request.getSession().setAttribute("msg", authority);
			request.getSession().setMaxInactiveInterval(2000000);
			
		}
		response.sendRedirect(url);
	}
}
