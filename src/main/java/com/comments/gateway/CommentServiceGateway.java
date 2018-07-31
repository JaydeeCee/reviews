package com.comments.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.comments.amazon.AmazonService;
import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.commons.Errorcodes;
import com.comments.domain.Result;
import com.comments.youtube.YoutubeService;

@Service
public class CommentServiceGateway {
	
	@Autowired
	private YoutubeService youtubeService;
	
	@Autowired
	private AmazonService amazonService;
	
	// route request to either youtube or amazon service
	public ResponseEntity<Result<List<CommentExtract>>> routeCommentToService(RequestEntity<CommentRequest> request) {
		Result<List<CommentExtract>> result = new Result<>();
		
		try {
			String url = request.getBody().getUrl();
			
			if(url.contains("www.youtube.com")) {
				return this.youtubeService.getYoutubeResponses(request);
			} else if(url.contains("www.amazon.com")){
				return this.amazonService.getAmazonReviews(request);
			} else {
				result.setErrorCode(Errorcodes.INVALID_REQUEST_URL);
				result.setStatus(false);
				result.setErrorDescription("This Application works for only Youtube and Amazon reviews or comments");
			}
		} catch(Exception e) {
			e.printStackTrace(System.err);
			
			result.setErrorCode(Errorcodes.INVALID_REQUEST_URL);
			result.setStatus(false);
			result.setErrorDescription("This Application works for only Youtube and Amazon reviews or comments");
		}
			return ResponseEntity.ok(result);
		}
}
