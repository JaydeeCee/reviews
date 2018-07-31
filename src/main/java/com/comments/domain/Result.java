package com.comments.domain;

import com.comments.commons.Errorcodes;

public class Result<T> {
	
	private Boolean status;
	private String message;
	private T data;
	private Errorcodes errorCode;
	private String errorDescription;
	
	
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Errorcodes getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Errorcodes errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	

}
