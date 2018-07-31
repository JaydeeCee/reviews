package com.comments.amazon;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.domain.Result;

public interface AmazonService {
	
	ResponseEntity<Result<List<CommentExtract>>> getAmazonReviews(RequestEntity<CommentRequest> request);

}
