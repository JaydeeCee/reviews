package com.comments.threads;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.commons.CommentExtract;
import com.comments.csv.CsvService;
import com.comments.email.EmailService;

@Service
public class ExecutorServiceBean {
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ExecutorServiceBean.class);
	
	
	@Autowired
	private EmailService emailService;
	
	
	
	public void splitProcessEmailAndCsv(String recipientEmailAddress, String videoId, String csvFileName, List<CommentExtract> listCommentExtract) {
		
		LOGGER.debug("Now at the Executor Service.");
		
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		try {
		
			Runnable runnableTasks = () -> {
			
			LOGGER.debug("Dev a Runnable.");
			
			
			
				
				// write comments to csv
				CsvService.writeCsvFile(csvFileName, listCommentExtract);
				
				// send CSV as email
				try {
					this.emailService.sendEmailWithAttachment(recipientEmailAddress, videoId, csvFileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				LOGGER.debug("Executor Operation Service completed.");	
				// shutdown executor service
				executorService.shutdown();
			};
		
		
		// start task
		executorService.execute(runnableTasks);
		
		
		// shutdown executor service
		//executorService.shutdown();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace(System.err);
	
			
	}
	};

}
