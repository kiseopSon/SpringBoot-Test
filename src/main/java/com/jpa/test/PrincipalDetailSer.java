package com.jpa.test;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailSer implements UserDetailsService{
	private final SpringDataTestRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("시큐리티 보안서비스 로직 진입");
		testVO vo = repo.findByUsername(username);
		
		return new principalDetailsVO(vo);
	}
}
