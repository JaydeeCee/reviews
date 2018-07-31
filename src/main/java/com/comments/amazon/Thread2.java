package com.comments.amazon;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.comments.commons.CommentExtract;
import com.comments.threads.ExecutorServiceBean;


public class Thread2 extends Thread{
	
	@Autowired
	private AmazonScrapper amazonScrapper;
	
	@Autowired
	private AmazonReviewRepository amazonReviewRepository;
	
	@Autowired
	private ExecutorServiceBean executorServiceBean;
	
	//private String totalPageNumbers;
	private String videoId;
	//private String baseUrl;
	private String recipientEmailAddress;
	private String csvFileName;
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AmazonExecutionServiceBean.class);
	private List<CommentExtract> comments = new ArrayList<>();
	
	public Thread2(AmazonThreadServiceParams params) {
		videoId = params.getVideoId();
		recipientEmailAddress = params.getRecipientEmailAddress();
		csvFileName = params.getCsvFileName();
	}
	
	
	@Override
	public void run() {
		LOGGER.info("Thread " + this.getName() + " started");
		List<AmazonScrapperEntity> reviewEntities = this.amazonReviewRepository.findByBufferid(videoId);
		comments = this.amazonScrapper.mapScrapperToComment(reviewEntities);
		this.executorServiceBean.splitProcessEmailAndCsv(recipientEmailAddress, videoId, csvFileName, comments);
	}
	
}
