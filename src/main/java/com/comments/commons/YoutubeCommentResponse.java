package com.comments.commons;

import java.io.Serializable;
import java.util.List;

public class YoutubeCommentResponse implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7558596282882758142L;
	
	
	private String kind;
	private String etag;
	private String nextPageToken;
	private PageInfo pageInfo;
	private List<Items> items;
	
	
	
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getNextPageToken() {
		return nextPageToken;
	}
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
	
	
	
	
	
	
}
