package com.comments.commons;

import java.io.Serializable;

public class TopLevelComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8243888730418774250L;
	
	
	private String kind, etag, id;
	private Clone snippet;
	
	
	
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
	public Clone getSnippet() {
		return snippet;
	}
	public void setSnippet(Clone snippet) {
		this.snippet = snippet;
	}
	
	
	
	
	
}
