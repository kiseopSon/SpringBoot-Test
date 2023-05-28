package com.email.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

//@ComponentScan("com.email") = 해당 스캔은 메인 메서드있는경로에선 없어도 되나, 벗어나면 해줘야 검색이가능
@SpringBootApplication
public class EmailTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailTestApplication.class, args);
	}

}
