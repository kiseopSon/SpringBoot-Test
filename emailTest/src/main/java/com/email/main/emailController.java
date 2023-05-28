package com.email.main;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller//restController로하면 resources 못읽음, 반드시 controller로해야 컨테이너가 서칭 가능 
@EnableAsync
public class emailController {
	
	private static final Logger log = LoggerFactory.getLogger(emailController.class);
	
	@Autowired
	private emailService emailservice;
	
	
//	@GetMapping(value = "/{id}")
//	public ModelAndView pageMove1(@PathVariable String id) {
//		System.out.println("asdf2");
//		log.error("test" + "여기 들어오니?");
//		return null;
//	}
//	
	@GetMapping(value = "/emailMain/{id}")
	public String pageMove2(@PathVariable String id, Model model, HttpServletResponse response) throws IOException {
		System.out.println("asdf3");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		emailservice.sendMail("srs7139@naver.com", "test", "test내용이다");
		model.addAttribute("result", out);
		return "emailtest";
	}
}
