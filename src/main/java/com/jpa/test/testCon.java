package com.jpa.test;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class testCon {
	
	private final testSer ser;
	
	@GetMapping(value = "/main")
	public String main(testVO test) throws Exception {
		//Content-Type = application/json 이어야 오브젝트형태로 받을 수 있음 / @requestBody 필요없음
		System.out.println("여기는 메인");
		Optional<String> result = ser.testText(test.toString());
		System.out.println(result);
		System.out.println(test.getId());
		System.out.println(test.getUsername());
		
		return "test/main";
	}
	
	@GetMapping(value = "/main/new")
	public String newMain() throws Exception {
		System.out.println("여기는 mainNew");
		return "test/mainCreate";
	}
	
	@GetMapping(value = "/main/list")
	public String list(Model model) throws Exception {
		List<testVO> vo = ser.findTestVOs();
		System.out.println(vo);
		//2개이상 데이터 일경우, [testVO(id=1, name=손기섭), testVO(id=2, name=손기섭2)] = testVO는 클래스로 잘 이쁘게 담는것뿐 뿌려주는역할엔 의미가없다.
		model.addAttribute("vo", vo);
		return "test/mainList";
	}
	
}
