package com.comments.commons;

import java.io.Serializable;
import java.util.Date;

// comment or review extract
public class CommentExtract implements Serializable {

	/**
	 * @author JayDee
	 */
	private static final long serialVersionUID = -6046069799065389205L;
	
	private String username;
	private Date date;
	private String rating, comment, link;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	
}
