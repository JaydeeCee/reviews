package com.comments.commons;

import java.io.Serializable;
import java.util.Date;

public class Clone implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7081811341858916620L;
	
	
	private String authorDisplayName, authorProfileImageUrl, authorChannelUrl;
	private AuthorChannelId authorChannelId;
	private String videoId, textDisplay, textOriginal;
	private Boolean canRate;
	private String viewerRating;
	private int likeCount;
	private Date publishedAt, updatedAt;
	
	
	
	
	
	public String getAuthorDisplayName() {
		return authorDisplayName;
	}
	public void setAuthorDisplayName(String authorDisplayName) {
		this.authorDisplayName = authorDisplayName;
	}
	public String getAuthorProfileImageUrl() {
		return authorProfileImageUrl;
	}
	public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
		this.authorProfileImageUrl = authorProfileImageUrl;
	}
	public String getAuthorChannelUrl() {
		return authorChannelUrl;
	}
	public void setAuthorChannelUrl(String authorChannelUrl) {
		this.authorChannelUrl = authorChannelUrl;
	}
	public AuthorChannelId getAuthorChannelId() {
		return authorChannelId;
	}
	public void setAuthorChannelId(AuthorChannelId authorChannelId) {
		this.authorChannelId = authorChannelId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTextDisplay() {
		return textDisplay;
	}
	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}
	public String getTextOriginal() {
		return textOriginal;
	}
	public void setTextOriginal(String textOriginal) {
		this.textOriginal = textOriginal;
	}
	public Boolean getCanRate() {
		return canRate;
	}
	public void setCanRate(Boolean canRate) {
		this.canRate = canRate;
	}
	public String getViewerRating() {
		return viewerRating;
	}
	public void setViewerRating(String viewerRating) {
		this.viewerRating = viewerRating;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public Date getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	
}
