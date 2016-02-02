package com.notice.mail.test;

import com.notice.mail.ISendEmail;
import com.notice.mail.SenderFactory;

public class MailTest {

	public static void main(String[] args) {
		ISendEmail sender = SenderFactory.getInstance().getSender("163");
		sender.send("sherlockchang@163.com"," [Current Test Mail][Report machine State]", "This is a Mail Test! Please Ignore when you recieved it.");
	}

}
