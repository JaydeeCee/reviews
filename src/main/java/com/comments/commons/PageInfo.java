package com.comments.commons;

import java.io.Serializable;

public class PageInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5489075208731225403L;
	
	
	private int totalResults;
	private int resultsPerPage;
	
	
	
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	
	
	
	
	
	

}
