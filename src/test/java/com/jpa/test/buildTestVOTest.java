package com.jpa.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class buildTestVOTest {
	
	@Autowired
	SpringDataTestRepo repo;
	
	@AfterEach
	public void clean() {
		repo.deleteAll();
	}
	
	@Test
	public void saveAndRead() {
		long id = 1;
		String name = "이름";
		
		repo.save(testVO.builder().id(id).name(name).build());
		
		List<testVO> result = repo.findAll();
		
		testVO test = result.get(0);
		
		assertThat(test.getId()).isEqualTo(id);
		assertThat(test.getUsername()).isEqualTo(name);
		
	}
}
