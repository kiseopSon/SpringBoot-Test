package com.jpa.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

//일반 레포 테스트 - db연결안된것임
public class MemoryTestVORepoTest {
	testRepoImpl repo = new testRepoImpl();
	testSerImpl ser = new testSerImpl(repo);
	
	@AfterEach
	public void delete() {
		System.out.println("delete_after-each_Test");
		repo.clear();
	}
	
	@Test
	public void save() {
		System.out.println("save_Test");
		testVO vo = new testVO();
		vo.setUsername("test중입니다.");
		
		repo.save(vo);
		testVO result = repo.findById(vo.getId()).get();
		System.out.println(result);
		Assertions.assertThat(vo).isEqualTo(result);
	}
	
	@Test
	public void findAll() {
		System.out.println("findAll_Test");
		testVO vo = new testVO();
		vo.setUsername("첫번쨰");
		repo.save(vo);
		
		testVO vo2 = new testVO();
		vo2.setUsername("2번쨰");
		repo.save(vo2);
		
		List<testVO> result = repo.findByAll();
		Assertions.assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	void join() throws Exception {
		System.out.println("join_test");
		//given - 이런 조건에서
		testVO vo = new testVO();
		vo.setUsername("join 테스터");
		
		//when - 이거를 실행했을때
		Long saveId = ser.join(vo);
		
		
		//then - 이런 결과가 나와야해
		testVO findTest = ser.findOne(saveId).get();
		assertThat(vo.getUsername()).isEqualTo(findTest.getUsername());
		
	}
	
	@Test
	public void 중복_회원_체크() throws Exception {
		testVO vo = new testVO();
		vo.setUsername("join 테스터");
		
		testVO vo2 = new testVO();
		vo2.setUsername("join 테스터");
		
		ser.join(vo);
		
		//예외확인 방법1
//		try {
//			ser.join(vo2);//동일한 이름임으로 예외 터짐
//			fail();
//		} catch (IllegalStateException e) {
//			assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");//성공하면 아무것도 안뜨고 초록불로 표시만 됨
//		}
		
		//예외확인 방법2 - 더 간편
		IllegalStateException e =  assertThrows(IllegalStateException.class, () -> ser.join(vo2));
		
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		
	}
	
	@Test
	void findTestVOs() {}
	
	@Test
	void findOne() {}
}