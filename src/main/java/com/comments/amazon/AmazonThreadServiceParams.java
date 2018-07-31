package com.comments.amazon;

import java.io.Serializable;
import java.util.List;

public class AmazonThreadServiceParams  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2889307552873472062L;
	
	
	private String totalPageNumbers, videoId, baseUrl;
	private List<String> amazonUrls;
	
	private String recipientEmailAddress, csvFileName;
	
	

	public String getTotalPageNumbers() {
		return totalPageNumbers;
	}

	public void setTotalPageNumbers(String totalPageNumbers) {
		this.totalPageNumbers = totalPageNumbers;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public List<String> getAmazonUrls() {
		return amazonUrls;
	}

	public void setAmazonUrls(List<String> amazonUrls) {
		this.amazonUrls = amazonUrls;
	}

	public String getRecipientEmailAddress() {
		return recipientEmailAddress;
	}

	public void setRecipientEmailAddress(String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}
	
	
	
	
	
	

}
