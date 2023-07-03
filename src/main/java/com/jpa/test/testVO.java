package com.jpa.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity//해당클래스명으로 테이블이 생성됨
@RequiredArgsConstructor
public class testVO {
	@Id @GeneratedValue
	private long id;
	private String username;
	private String password;
	private String role;

	//테스트 확인용1
	public long getId() {
		return id;
	}

	//DATA-JPA 새로운 insert 주입용
	public String getUsername() {
		return username;
	}
	
	@Builder
	testVO (long id, String name){
		this.id = id;
		this.username = name;
	}
	
	public List<String> getRoleList(){
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
	
}
