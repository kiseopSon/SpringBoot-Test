package com.email.sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class emailController {
	
	private static final Logger log = LoggerFactory.getLogger(emailController.class);
	
	
//	@GetMapping(value = "/{id}")
//	public ModelAndView pageMove1(@PathVariable String id) {
//		System.out.println("asdf2");
//		log.error("test" + "여기 들어오니?");
//		return null;
//	}
//	
	@GetMapping(value = "/emailSub/{id}")
	public String pageMove2(@PathVariable String id, Model model) {
		System.out.println("asdf3");
		log.error("test" + "여기 들어오니?");
		model.addAttribute("result", "손기섭");
		return "emailtest";
	}
}
