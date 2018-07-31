package com.comments.amazon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AmazonScrapperEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7661022305534539276L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String bufferid;
	
	private String ratings;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date reviewdate;
	
	@Column(columnDefinition = "TEXT")
	private String reviewtext;
	
	
	private String author;
	
	@Column(columnDefinition = "TEXT")
	private String link;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBufferid() {
		return bufferid;
	}
	public void setBufferid(String bufferid) {
		this.bufferid = bufferid;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	public Date getReviewdate() {
		return reviewdate;
	}
	public void setReviewdate(Date reviewdate) {
		this.reviewdate = reviewdate;
	}
	public String getReviewtext() {
		return reviewtext;
	}
	public void setReviewtext(String reviewtext) {
		this.reviewtext = reviewtext;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	
	

}
