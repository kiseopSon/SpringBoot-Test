package com.jpa.test;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//인터페이스가 인터페이스 하위 구현체만들고 싶으면 extends -> 다중상속가능 - JpaRepository이건 원래 있는것임 내가만든게 아님
//JpaRepository안에 있는걸 보고 구현체를 알아서만들어줌
//Long = pk타입
public interface SpringDataTestRepo extends JpaRepository<testVO, Long>{
	//앞에 접두어 findBy~~ -> JPQL = select m from test m where m.name = ?
	
	testVO findByUsername(String username);
}
