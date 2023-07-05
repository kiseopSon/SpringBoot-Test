package com.jpa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
public class cont {
	
	private UserMapper user;
	
	public cont(UserMapper user) {
		this.user = user;
	}
	
	@GetMapping(value = "/")
	@ResponseBody
	public List<HashMap<String, Object>> result() throws InstantiationException, IllegalAccessException{
		List<HashMap<String, Object>> result = user.findAll();
		System.out.println(result);
		return result;
	}
	
}
