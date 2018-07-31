package com.comments.email;

public interface EmailService {
	
	void sendEmailWithAttachment(String recipientEmailAddress, String videoId, String fileName) throws Exception;

}
