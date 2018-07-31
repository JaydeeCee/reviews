package com.comments.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.domain.Result;
import com.comments.gateway.CommentServiceGateway;

@Controller
public class CommentController {
	
	@Autowired
	private CommentServiceGateway serviceGateway;
	
	
	// youtube or amazon request entry point
	@CrossOrigin
	@PostMapping(value="getcomments", produces = MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Result<List<CommentExtract>>> getComments(RequestEntity<CommentRequest> request) {
		return this.serviceGateway.routeCommentToService(request);
	}

}
