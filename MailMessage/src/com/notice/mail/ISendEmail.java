package com.notice.mail;

public interface ISendEmail {

	/**
	 * 
	 * @param to split with ',' if have multiple senders
	 * @param subject title of mail
	 * @param content content text
	 */
	void send(String to,String subject, String content);

}
