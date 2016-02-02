package com.notice.mail.sender;

import com.notice.mail.ISendEmail;

public class DummyEmailSender implements ISendEmail {

	@Override
	public void send(String to, String subject, String content) {
		System.out.println("[DummyEmailSender]:" + content);
	}
	
}
