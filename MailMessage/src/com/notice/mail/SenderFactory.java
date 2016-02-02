package com.notice.mail;

import com.notice.mail.sender.DummyEmailSender;
import com.notice.mail.sender.GmailSender;
import com.notice.mail.sender.OutlookSender;
import com.notice.mail.test.Mail163Sender;

public class SenderFactory {

	public static final String GMAIL = "gmail";
	public static final String MAIL163 = "163";
	public static final String OUTLOOK = "outlook";
	public volatile static SenderFactory mInstance = null;

	public static SenderFactory getInstance() {

		if (mInstance == null) {
			synchronized (SenderFactory.class) {
				if (mInstance == null) {
					mInstance = new SenderFactory();
				}
			}
		}

		return mInstance;
	}

	private SenderFactory() {

	}

	public ISendEmail getSender(String key) {

		switch (key) {
		case GMAIL:
			return new GmailSender();
		case MAIL163:
			return new Mail163Sender();
		case OUTLOOK:
			return new OutlookSender();
		default:
			return new DummyEmailSender();
		}
	}

}
