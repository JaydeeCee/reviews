package com.comments.amazon;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.comments.commons.AmazonUrlBuildParams;
import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.domain.Result;
import com.comments.utils.Json;


// Service to get amazon Reviews after scraping is done
@Service
public class AmazonServiceBean implements AmazonService {
	
	@Autowired
	private AmazonScrapper amazonScrapper;
	
	
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AmazonServiceBean.class);
	
	// method to return review params after scraping
	@Override
	public ResponseEntity<Result<List<CommentExtract>>> getAmazonReviews(RequestEntity<CommentRequest> request) {
		
		Result<List<CommentExtract>> amazonReviews = new Result<>();
	
		
		try {
		
			String requestUrl = request.getBody().getUrl();
			LOGGER.debug(Json.toJson(requestUrl));
			
			// get urlPArams from requestUrl
			AmazonUrlBuildParams buildParams = this.getAmazonUrlParam(requestUrl);
			
			// scrap Amazon website
			amazonReviews = this.amazonScrapper.scrapperBuild(buildParams.getVideoId(), buildParams.getBaseUrl(), request.getBody().getEmailAddress());
			
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
		
		return ResponseEntity.ok(amazonReviews);
	}
	
	
	
	// get AmazonUrl build Params
	public AmazonUrlBuildParams getAmazonUrlParam(String requestUrl) {
		
		int dpIndex = requestUrl.indexOf("/dp/");
		int refIndex = requestUrl.indexOf("ref");
		String rootUrl = requestUrl.substring(0, dpIndex+1);
		String videoId = requestUrl.substring(dpIndex +4, refIndex-1);
		LOGGER.debug("dpindex " + Json.toJson(dpIndex));
		LOGGER.debug("refIndex " + refIndex);
		LOGGER.debug("rootUrl " + rootUrl);
		LOGGER.debug("videoId " + videoId);
		
		AmazonUrlBuildParams buildParams = new AmazonUrlBuildParams();
		
		buildParams.setBaseUrl(rootUrl);
		buildParams.setVideoId(videoId);
		
		return buildParams;
	}
}
