package com.comments.email;

import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceBean implements EmailService {
	
	
	private final static Logger LOGGER  = LoggerFactory.getLogger(EmailServiceBean.class);
	
	@Autowired
	private JavaMailSender emailsender;
	
	
	@Override
	public void sendEmailWithAttachment(String recipientEmailAddress, String videoId, String fileName) throws Exception {
		
		LOGGER.debug("Now at the Email Service.");
		Date date = Calendar.getInstance().getTime();
		MimeMessage message = emailsender.createMimeMessage();
		
		
		//enable multi-part flag
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(recipientEmailAddress);
		helper.setText("Your Video Reviews/Comment with Video Id " + videoId + " is hereby attached in a CSV file");
		helper.setSubject(fileName + " " + date);
		
		// add attachment to mail
		FileSystemResource file = new FileSystemResource(fileName);
		helper.addAttachment(fileName, file);
		
		// send mail
		emailsender.send(message);
		LOGGER.debug("Email sent to recipient.");
		
	}

}
