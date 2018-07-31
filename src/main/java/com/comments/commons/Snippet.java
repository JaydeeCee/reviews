package com.comments.commons;

import java.io.Serializable;

public class Snippet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5687184470046533310L;
	
	
	private String videoId;
	private TopLevelComment topLevelComment;
	
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public TopLevelComment getTopLevelComment() {
		return topLevelComment;
	}
	public void setTopLevelComment(TopLevelComment topLevelComment) {
		this.topLevelComment = topLevelComment;
	}
	
	

}
