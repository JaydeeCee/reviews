package com.comments.amazon;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import com.comments.commons.CommentExtract;
import com.comments.commons.Errorcodes;
import com.comments.domain.Result;
import com.comments.threads.ExecutorServiceBean;

// this service is where the actual scrapig is done
@Service
public class AmazonScrapper {
	
	
	@Autowired
	private AmazonReviewRepository amazonReviewRepository;
	
	@Autowired
	private ExecutorServiceBean executorService;
	
	@Autowired
	private AmazonExecutionServiceBean amazonExecutorService;
	
	@Autowired
	private AmazonScrapper amazonScrapper;
	
	 private String pageNumber = "1";
	 private Boolean isPageNumber = true;

	private String totalPageNumbers = null;
	
	SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
	
	
	// build an amazon Review Page Url
	public String buildReviewUrl(String videoId, String baseUrl, String pageNumber) {
		
		String reviewUrl = baseUrl + "product-reviews/" + videoId + "/ref=cm_cr_arp_d_paging_btm_" + pageNumber + "?ie=UTF8&pageNumber=" + pageNumber + "&reviewerType=all_reviews";
		return reviewUrl;
	}
	
	
	
	// connect to Amazon website
	public Document connectToAmazonWebsite(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace(System.err);
			System.out.println("Could not connect:-- Read time out");
			return null;
		}
		return doc;
	}
	
	
	// retrieve first page review and numbers of review pages
	public Result<List<CommentExtract>> scrapperBuild(String videoId, String baseUrl, String recipientEmailAddress) {
		
		
		// empty database of entries with same videoId
		List<AmazonScrapperEntity> reviewEntities = this.amazonReviewRepository.findByBufferid(videoId);
		this.amazonReviewRepository.deleteAll(reviewEntities);
		
			Result<List<CommentExtract>> result = new Result<>();
			List<CommentExtract> comments = new ArrayList<>();
			this.isPageNumber = true;
		
			Document doc = null;
			int attemptIndex = 0;

		
			try {
				
			
			String url = this.buildReviewUrl(videoId, baseUrl, pageNumber);
			
			// keep attempting to download website
			do {
				doc = this.connectToAmazonWebsite(url);
				attemptIndex++;
				System.out.println("Attempt to connect to Amazon:- " + attemptIndex);
			} while(doc == null && attemptIndex < 10);
		
			
			// scrape amazon webpage
			List<AmazonScrapperEntity> listReviews = this.scrapeAmazonWebsite(doc, videoId);
			comments = this.amazonScrapper.mapScrapperToComment(listReviews);
			
			
			listReviews.forEach((review) -> {
				this.amazonReviewRepository.save(review);
			});
			
			
			
			if(listReviews == null || listReviews.isEmpty()) {
				result.setStatus(false);
				result.setErrorCode(Errorcodes.ERROR_RETRIEVING_RECORDS);
				result.setErrorDescription("Unable to fetch Records at this time. Please check internet connection");
				return result; 
			}
				
				
			
						
			if(isPageNumber) {
				String ddvid = RandomStringUtils.randomAlphanumeric(8);
				String csvFileName = "Amazon Reviews " + ddvid + " .csv";
				
				// determine the total number of pages of reviews that exist
				List<String> pageNumbers = this.determinePageNumbers(doc);
				
				totalPageNumbers = pageNumbers.get(pageNumbers.size()- 1);
				
				// If pageNumbers has more than one entry, There are more than 10 reviews and other pages exists. hence multi thread 
				if(pageNumbers.size() > 1) {
					AmazonThreadServiceParams params = new AmazonThreadServiceParams();
					params.setBaseUrl(baseUrl);
					params.setCsvFileName(csvFileName);
					params.setRecipientEmailAddress(recipientEmailAddress);
					params.setTotalPageNumbers(totalPageNumbers);
					params.setVideoId(videoId);
					
					// multi-Thread amazon pages. 
					this.isPageNumber = false;
					this.amazonExecutorService.amazonMultiThread(params);

					
				} else {
					this.isPageNumber = false;
					// all reviews available on Page 1. Prepare reviews for Response Entity type
					comments = this.mapScrapperToComment(listReviews);
					
					
					// start new thread for CSV creation and Sending Email
					this.executorService.splitProcessEmailAndCsv(recipientEmailAddress, videoId, csvFileName, comments);
			
					result.setData(comments);
					result.setStatus(true);
					result.setMessage("Amazon Reviews Retrieved");
					this.isPageNumber = true;  // return checker to true.
					return result;
				}
				
					System.out.println("Page Number check " + this.isPageNumber);
					System.out.println("Total Number of Pages: " + totalPageNumbers);
				}	
			} catch(Exception e) {
				e.printStackTrace(System.err);
			}
			this.isPageNumber = true;
			result.setData(comments);
			result.setStatus(true);
			result.setMessage("First part of reviews retrieved. Full list is being processed to be sent to your mail for download in a short while");
			return result;
	}
	
	
	
	// build all Amazon url. Method builds all urls of review pages available 
	public List<String> buildAllAmazonPagesUrl(String totalPages, String videoId, String baseUrl) {
		String totalPagesNumber = totalPages;
		List<String> amazonPagesUrl = new ArrayList<>();
		
		for(int i= 2; i<= Integer.parseInt(totalPagesNumber); i++) {
			String url = this.buildReviewUrl(videoId, baseUrl, String.valueOf(i));
					
			amazonPagesUrl.add(url);
			
			//System.out.println(url);
		}
		return amazonPagesUrl;
	}
	
	
	
	
	// method to scrape the website into review parameters
	public List<AmazonScrapperEntity> scrapeAmazonWebsite(Document doc, String videoId) {
		
		List<AmazonScrapperEntity> amazonBuffer = new ArrayList<>();
		
		// check to see if html element was downloaded
		if(doc == null) {
			return null;
		}
				
		try {		
		Elements reviewElements = doc.select("div#cm_cr-review_list");
		
		
		
		int length = reviewElements.first().getElementsByClass("review-text").size();
		System.out.println("Lenght of reviews= " + length);
		
		if(reviewElements == null || reviewElements.isEmpty()) {
			return null;
		}
		
		for(int i=0; i< length; i++) {
			AmazonScrapperEntity buffer = new AmazonScrapperEntity();
			String reviewRatings = reviewElements.first().getElementsByClass("a-icon-alt").get(i).text();
			String reviewAuthor = reviewElements.first().getElementsByClass("author").get(i).text();
			String reviewDate = reviewElements.first().getElementsByClass("review-date").get(i).text();
			String reviewDateSplit = reviewDate.substring(3, reviewDate.length());
			String reviewText = reviewElements.first().getElementsByClass("review-text").get(i).text();
			String authorLink = reviewElements.first().select(".author").get(i).attr("abs:href");
			
			
		//	System.out.println("reviewRatings " + reviewRatings + " " + " author " + reviewAuthor + " reviewDate " + reviewDate + " reviewText " + reviewText);
			
			buffer.setRatings(reviewRatings);
			buffer.setAuthor(reviewAuthor);
			Date date = formatter.parse(reviewDateSplit);
			buffer.setReviewdate(date);
			buffer.setReviewtext(reviewText);
			buffer.setLink(authorLink);
			buffer.setBufferid(videoId);
			amazonBuffer.add(buffer);
			}
		}
		catch(Exception e) {
			e.printStackTrace(System.err);
		}
		return amazonBuffer;
	}
	
	
	
	
	// determine Page Numbers
	private List<String> determinePageNumbers(Document doc) {
		List<String> pageNumbers = new ArrayList<>();
		try {
			Elements reviewElements = doc.select("div#cm_cr-review_list");
			
			
			int amazonPagesButtons = 5;
			int pageNumbersFind = reviewElements.first().getElementsByClass("page-button").size();
			System.out.println(pageNumbersFind);
			
			if(pageNumbersFind == 0) {
				String pageNumber = "1";
				pageNumbers.add(pageNumber);
			} else {
				for(int i=0; i< amazonPagesButtons; i++) {
					String pageNumber = reviewElements.first().getElementsByClass("page-button").get(i).text();
					pageNumbers.add(pageNumber);
				}
			}
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
		return pageNumbers;
	}
	
	
	// map review Elements to Response Entity type.
	public List<CommentExtract> mapScrapperToComment(List<AmazonScrapperEntity> reviews) {
		List<CommentExtract> comments = new ArrayList<>();		
		List<AmazonScrapperEntity> listReviews = reviews;
		
		System.out.println("Now mapping amazon reviews to comments");
		for(AmazonScrapperEntity buffer: listReviews) {
			CommentExtract commentExtract = new CommentExtract();
			commentExtract.setComment(buffer.getReviewtext());
			commentExtract.setDate(buffer.getReviewdate());
			commentExtract.setLink(buffer.getLink());
			commentExtract.setUsername(buffer.getAuthor());
			commentExtract.setRating(buffer.getRatings());
			comments.add(commentExtract);
		}
		return comments;
	}

}
