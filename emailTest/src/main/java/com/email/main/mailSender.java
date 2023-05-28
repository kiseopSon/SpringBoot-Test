package com.email.main;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;

@Repository
public class mailSender  extends JavaMailSenderImpl{
	public mailSender() {
		setHost("smtp.naver.com");
		setPort(587);
		setUsername("srs7139@naver.com");
		setPassword("tkrhk12!");
		Properties propery = getJavaMailProperties();
		propery.setProperty("mail.transport.protocol", "smtp");
		propery.setProperty("mail.smtp.auth", "true");
		propery.setProperty("mail.smtp.starttls.enable", "true");
		propery.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		propery.setProperty("mail.debug", "true");
	}	
}
