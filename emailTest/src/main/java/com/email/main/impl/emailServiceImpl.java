package com.email.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.email.main.emailService;
import com.email.main.mailSender;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class emailServiceImpl implements emailService{
	
	@Autowired
	private mailSender sender;
	
	@Override
	@Async
	public void sendMail(String url, String title, String text) {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper messageHelp = new MimeMessageHelper(message, true, "utf-8");
			//messageHelp.setCc(url);//참조인
			messageHelp.setFrom(url, "테스트");//보낸사람
			messageHelp.setSubject(title);//이메일 제목
			//mailSender의 host 주소가 다를경우 스팸으로 빠짐
			messageHelp.setTo("icarusdoll@gmail.com");//받는사람
			messageHelp.setText(text);//이메일 내용
			sender.send(message);
		} catch (Exception e) {
			System.out.println("sendMail.Exception : " + e.getMessage());
		}
	}

}
