package com.comments.youtube;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.comments.amazon.AmazonExecutionServiceBean;
import com.comments.gateway.CommentServiceGateway;


@Configuration
public class YoutubeBuilderImpl {
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	@Bean
	public CommentServiceGateway commentServiceGateway() {
		return new CommentServiceGateway();
	}
	
	
	
	
}
