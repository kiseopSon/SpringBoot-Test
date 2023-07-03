package com.jpa.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class testApi {
	
	private final testSer ser;
	
	@PostMapping(value = "/api/{test}")
	public String api(testVO vo, @PathVariable("test") String test) {
		//@PathVariable = 위 매핑{}와 매칭시켜주는것이고, 알아서 기본통신은 get이기에 postman같은 통신서비스를 통해서 post테스트를 해야 정상 작동 확인할 수 있음.
		System.out.println("여기는 api : url : " + test);
		System.out.println(vo);
		
		testVO newVO = new testVO();
		newVO.setUsername(vo.getUsername());
		ser.join(newVO);
		
		return "redirect:/main";
	}
}
