package com.comments.youtube;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.commons.Errorcodes;
import com.comments.commons.Items;
import com.comments.commons.YoutubeCommentResponse;
import com.comments.domain.Result;
import com.comments.threads.ExecutorServiceBean;

@Service
public class YoutubeServiceBean implements YoutubeService{
	
	@Value("${youtube_api_url}")
	private String youtube_api_url;
	
	@Value("${youtube_api_key}")
	private String youtube_api_key;
	
//	private final static Logger LOGGER = LoggerFactory.getLogger(YoutubeServiceBean.class);
	private String pageToken = null;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExecutorServiceBean executorService;
	
	
	
	
	@Override
	public ResponseEntity<Result<List<CommentExtract>>> getYoutubeResponses(RequestEntity<CommentRequest> request) {
		Result<List<CommentExtract>> result = new Result<>();
		List<CommentExtract> listCommentExtract = new ArrayList<>();
		
		try {
			String requestUrl = request.getBody().getUrl();
			String recipientEmailAddress = request.getBody().getEmailAddress();
			
			if(requestUrl.contains("www.youtube.com")) {
				String videoIdTrim = requestUrl.substring(requestUrl.indexOf("="));
				String videoId = videoIdTrim.substring(1, videoIdTrim.length());
				
			
				// get all comments from youtube API
				do {
					YoutubeCommentResponse videoComments = this.getYoubeResponses(videoId, pageToken);
					
					pageToken = videoComments.getNextPageToken();
					List<Items> commentItems = videoComments.getItems();
					
					commentItems.forEach((item) -> {
						CommentExtract commentExtract = new CommentExtract();
						commentExtract.setUsername(item.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
						commentExtract.setComment(item.getSnippet().getTopLevelComment().getSnippet().getTextDisplay());
						commentExtract.setDate(item.getSnippet().getTopLevelComment().getSnippet().getPublishedAt());
						if(item.getSnippet().getTopLevelComment().getSnippet().getViewerRating() == null) {
							commentExtract.setRating("none");
						} else {
							commentExtract.setRating(item.getSnippet().getTopLevelComment().getSnippet().getViewerRating());
						}
						commentExtract.setLink("none");
						
						listCommentExtract.add(commentExtract);
					});
				} while(pageToken != null);
				
				
				String ddvid = videoId;
				String csvFileName = "Youtube Comments " + ddvid + " .csv";
				
				// start new thread for CSV creation and Sending Email
				this.executorService.splitProcessEmailAndCsv(recipientEmailAddress, videoId, csvFileName, listCommentExtract);
				
				
				// set result
				result.setData(listCommentExtract);
				result.setStatus(true);
				result.setMessage("Youtube Comments Retrieved");  
				
			} else {
				result.setErrorCode(Errorcodes.INVALID_REQUEST_URL);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			result.setStatus(false);
			result.setErrorCode(Errorcodes.ERROR_RETRIEVING_RECORDS);
			
		}
		return ResponseEntity.ok(result); 
	
	} 
	
	
	
	// get comments and reviews from youtube
	private YoutubeCommentResponse getYoubeResponses(String yVideoId, String pageToken) {
	
		
		
		ResponseEntity<YoutubeCommentResponse> videoResponse = null;
		YoutubeCommentResponse videoComments = null;
		
		
		try {
			UriComponents uriComponent = UriComponentsBuilder.newInstance()
					.uri(new URI(youtube_api_url))
					.queryParam("key", youtube_api_key)
					.queryParam("textFormat", "plainText")
					.queryParam("part", "snippet,replies")
					.queryParam("topLevelComment")
					.queryParam("maxResults", "100")
					.queryParam("videoId", yVideoId)
					.build();
			
			UriComponents uriComponentPage = UriComponentsBuilder.newInstance()
					.uri(new URI(youtube_api_url))
					.queryParam("key", youtube_api_key)
					.queryParam("textFormat", "plainText")
					.queryParam("part", "snippet,replies")
					.queryParam("topLevelComment")
					.queryParam("maxResults", "100")
					.queryParam("videoId", yVideoId)
					.queryParam("pageToken", pageToken)
					.build();
			
			if(pageToken == null) {
				videoResponse = this.restTemplate.exchange(uriComponent.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<YoutubeCommentResponse>() {
				});
			} else {
				videoResponse = this.restTemplate.exchange(uriComponentPage.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<YoutubeCommentResponse>() {
				});
			}
			
			videoComments = videoResponse.getBody();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
		return videoComments;
	}
	
	
	
	
}
