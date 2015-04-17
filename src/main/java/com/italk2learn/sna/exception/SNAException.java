package com.italk2learn.sna.exception;

public class SNAException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception father, If applies.
	 */
	private Exception exception;
	
	private String snamessage;
	
	public SNAException(Exception exception, String mes) {
		super();
		this.exception = exception;
		this.snamessage = mes;
	}

	/**
	 * Private constructor .
	 */
	private SNAException() {
		super();
	}


	public Exception getException() {
		return exception;
	}


	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getSnamessage() {
		return snamessage;
	}

	public void setSnamessage(String snamessage) {
		this.snamessage = snamessage;
	}

}
