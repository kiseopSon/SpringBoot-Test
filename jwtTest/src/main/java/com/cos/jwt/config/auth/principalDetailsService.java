package com.cos.jwt.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class principalDetailsService implements UserDetailsService{
	
	//여기서부터 동영상 시청 - 스프링부트 시큐리티 24강 - jwt를 위한 로그인 시도
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("기본 deafult 로그인");
		User user = userRepository.findByUsername(username);
		
		return new principalDetails(user);
	}
	
}
