package com.jpa.test;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration//설정파일 : 이거잇으면 알아서 컨트롤에서부터 요청들어온 내용을 알아서 Di(의존성 주입)해줌
@RequiredArgsConstructor
public class SpringConfig {
//	data-jpa적용 전 필수
	private final EntityManager em;
	
//	data-jpa적용 후 -20230630 bean 중첩으로 현재 안됨, SpringDataTestRepo 인터페이스 파일 무쓸모. 
	//testRepo repo; //간단하게 구현체만 만들어주면 내부적으로 리플렉션동작해서 만들어준다는데 안됨.
	//tip1 : TDD설계해서 확인본 결과, 리플렉션말고, jparepository를 상속받은 인터페이스를 기존 레포를 쓰지말고 가져다 쓰면됨. = 현재는 뭔가 꼬여서 안되는것 같음.
	//tip2 : vo는 build패턴으로 선언해 놓으면, data-jpa하고 궁합이 잘맞음.
	//tip1의 결론은 결국 서비스별로 repo하고 repoImpl 만들지말고 jparepository인터페이스를 가져다 쓰자. 
	//= eGovFrameWork의 dao쓰는거랑 동일한 로직.
//	data-jpa적용 전 필수
	@Bean
	public testSer testService() {
		System.out.println("bean service 등록");
		return new testSerImpl(testRepository());
		// 20230630 bean 중첩으로 현재 안됨 .
		//return new testSerImpl(repo);
	}
	
	@Bean
	public testRepo testRepository() {//테스트를 할때 testRepo를 implement하고 있는 모든 구현체를 바꿔면 주면 된다.
		return new JpaTestVORepo(em);//이것만 바꿔주면 됨.
	}
	
	
}
         