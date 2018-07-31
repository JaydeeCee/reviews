package com.comments.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.commons.CommentExtract;
import com.comments.threads.ExecutorServiceBean;
import com.comments.utils.Json;


@Service
public class AmazonExecutionServiceBean {
	
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Autowired
	private AmazonThreadService amazonThreadService;
	
	@Autowired
	private AmazonScrapper amazonScrapper;
	
	@Autowired
	private AmazonReviewRepository amazonReviewRepository;
	
	@Autowired
	private ExecutorServiceBean executorServiceBean;
	

	private final static Logger LOGGER = LoggerFactory.getLogger(AmazonExecutionServiceBean.class);
	
	
	private List<CommentExtract> comments = new ArrayList<>();
	
	
	
	// main multi-thread method
	public void amazonMultiThread(AmazonThreadServiceParams params) {
		
		String totalPageNumbers = params.getTotalPageNumbers();
		String videoId = params.getVideoId();
		String baseUrl = params.getBaseUrl();
		String recipientEmailAddress = params.getRecipientEmailAddress();
		String csvFileName = params.getCsvFileName();
		
		// runnable Task 1
		Runnable threadTask1 = () -> {
			List<String> amazonUrls = this.amazonScrapper.buildAllAmazonPagesUrl(totalPageNumbers, videoId, baseUrl);
			this.amazonThreadService.ExecuteAmazonScapperCallable(amazonUrls, recipientEmailAddress, csvFileName, videoId);
		};
		
		
		// Runnable Task 2
		Runnable threadTask2 = () -> {
			List<AmazonScrapperEntity> reviewEntities = this.amazonReviewRepository.findByBufferid(videoId);
			LOGGER.debug("this is the number of reviewEntities " + Json.toJson(reviewEntities.size()));
			if(reviewEntities == null || reviewEntities.isEmpty()) {
				
			} else {
				comments = this.amazonScrapper.mapScrapperToComment(reviewEntities);
				this.executorServiceBean.splitProcessEmailAndCsv(recipientEmailAddress, videoId, csvFileName, comments);
			}
				
		}; 
		
		
		Thread thread1 = new Thread(threadTask1);
		Thread thread2 = new Thread(threadTask2);
		
		
		// mother runnable 
		Runnable motherRunnableTask = () -> {
			
			thread1.start();
			
			try {
				thread1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			thread2.start();
			
		};
		
		//submit mother task to executor Service
		LOGGER.debug("Mother Runnable executor Service started");
		executorService.execute(motherRunnableTask);
		
		
			
	}
}
