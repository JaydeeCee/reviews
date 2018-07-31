package com.comments.youtube;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.comments.commons.CommentExtract;
import com.comments.commons.CommentRequest;
import com.comments.domain.Result;

public interface YoutubeService {
	
	ResponseEntity<Result<List<CommentExtract>>> getYoutubeResponses(RequestEntity<CommentRequest> request);
}
