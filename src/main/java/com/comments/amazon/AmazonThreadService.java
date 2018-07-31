package com.comments.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.utils.Json;



@Service
public class AmazonThreadService {
	
	
	
	@Autowired
	private AmazonScrapper amazonScrapper;

	@Autowired
	private AmazonReviewRepository amazonReviewRepo;
	
	ExecutorService executorService = Executors.newCachedThreadPool();
	List<String> timedOutConnectionUrls  = new ArrayList<>();
	private int webPageCount = 1;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AmazonThreadService.class);
	
	
	
	private String SplitScrappingWork(String url, String email, String fileName, String videoId) {
		
		Document doc = null;
		int trials = 1;
		
		
		// Attempt connection to amazon webpage
		do {
			doc =  this.amazonScrapper.connectToAmazonWebsite(url);
			
			if(doc == null ) {
			
				System.out.println("Unable to connect to remote host: " + trials);
			
			} else {
				
				System.out.println("Connection successful on " + trials + " trial");
			}
	
			trials++;
		
		} while(doc == null && trials <=10);
		
		
		// terminate operation if connection is timed out or unavailable
		if(doc == null) {
			System.out.println("Giving up on connection attempt");
			return url;
		}
		
		
		// start scraping and return list of review Entities
		List<AmazonScrapperEntity> reviewEntities = this.amazonScrapper.scrapeAmazonWebsite(doc, videoId);
		
		
		// store each review in database
		reviewEntities.forEach((review) -> {
			
			this.amazonReviewRepo.save(review);
		});
		
		return null;
	}
	
	
	// make a callable interface
	private String getCallableTasks(String amazonUrl, String email, String fileName, String videoId) {
		String timedOutUrl = "";
		
		// make a callable
			Callable<String> callableTask = () -> {
				return this.SplitScrappingWork(amazonUrl, email, fileName, videoId);
			};
			
			try {
			// start task
			 Future<String> timedOutUrlsFuture = executorService.submit(callableTask);
			  timedOutUrl = timedOutUrlsFuture.get();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
			return timedOutUrl;	
	}
	
	
	
	public List<String> ExecuteAmazonScapperCallable(List<String> amazonUrl, String email, String fileName, String videoId) {
	
		List<String> amazonUrls = amazonUrl;
		int attemptIndex = 0;
		
		amazonUrls.forEach((url) -> {
			webPageCount++;
			LOGGER.debug("Webpage " + this.webPageCount +  " submitted to executor Service.");	
			String timedOutUrl = this.getCallableTasks(url, email, fileName, videoId);
			if(timedOutUrl != null) {
				timedOutConnectionUrls.add(timedOutUrl);
			} 
		});
		
		if((timedOutConnectionUrls != null  || timedOutConnectionUrls.isEmpty()) && attemptIndex <5) {
			attemptIndex++;
			System.out.println(timedOutConnectionUrls.size() + " webpages could not be gabbed");
			LOGGER.debug("Starting new Loop to grab webpage");
			webPageCount = 1;
			timedOutConnectionUrls.forEach((url) -> {
				webPageCount++;
				
				LOGGER.debug("Webpage " + this.webPageCount +  " submitted to executor Service.");	
				this.getCallableTasks(url, email, fileName, videoId);
				
			});
		}
		
		LOGGER.debug("Webpage " + this.webPageCount +  " completed.");	
		System.out.println("Total number of urls not connected " + timedOutConnectionUrls.size());
		webPageCount = 1;
		LOGGER.debug(Json.toJson(timedOutConnectionUrls));
		return timedOutConnectionUrls;
	}
	
}
	

