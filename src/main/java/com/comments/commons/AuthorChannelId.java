package com.comments.commons;

import java.io.Serializable;

public class AuthorChannelId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -881062136827986170L;
	
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
