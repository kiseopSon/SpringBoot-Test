package com.email.main;

import org.springframework.scheduling.annotation.Async;

public interface emailService {
	@Async
	public void sendMail(String url , String title, String text);
}
