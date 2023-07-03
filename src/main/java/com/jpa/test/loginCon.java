package com.jpa.test;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
//@CrossOrigin //cors허용
public class loginCon {
	private final SpringDataTestRepo repo;
	private final BCryptPasswordEncoder pwdEncoder;
	
	// 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@GetMapping("user")
	public String user(Authentication auth) {
		System.out.println("user GetMapping");
		principalDetailsVO vo  = (principalDetailsVO) auth.getPrincipal();
		System.out.println("계정정보 : " + vo.getUser().getId());
		System.out.println("계정아이디 : " + vo.getUser().getUsername());
		System.out.println("계정비밀번호 : " + vo.getUser().getPassword());
		return "<h1>유저</h1>";
	}
	
	//매니저 혹은 어드민만 접근가능
	@GetMapping("manager/reports")
	public String report() {
		System.out.println("manager GetMapping");
		return "<h1>매니저</h1>";
	}
	
	//어드민만 접속가능
	@GetMapping("admin/reports")
	public List<testVO> users() {
		System.out.println("admin GetMapping");
		return repo.findAll();
	}
	
	@PostMapping ("join")
	public String join(@RequestBody testVO vo) {
		vo.setPassword(pwdEncoder.encode(vo.getPassword()));
		vo.setRole("ROLE_USER");
		repo.save(vo);
		return "<h1>회원가입 완료</h1>";
	}
}
