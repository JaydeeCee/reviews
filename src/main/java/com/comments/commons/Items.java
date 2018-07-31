package com.comments.commons;

import java.io.Serializable;

public class Items implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5312626915090779323L;
	
	private String kind;
	private String etag;
	private String id;
	private Snippet snippet;
	private Boolean canReply;
	private int totalReplyCount;
	private Boolean isPublic;
	
	
	
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Snippet getSnippet() {
		return snippet;
	}
	public void setSnippet(Snippet snippet) {
		this.snippet = snippet;
	}
	public Boolean getCanReply() {
		return canReply;
	}
	public void setCanReply(Boolean canReply) {
		this.canReply = canReply;
	}
	public int getTotalReplyCount() {
		return totalReplyCount;
	}
	public void setTotalReplyCount(int totalReplyCount) {
		this.totalReplyCount = totalReplyCount;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
	
	
	
	
	
	
	
	

}
