package com.cognizant.moviecruiserserverapplication.exception;

@SuppressWarnings("serial")
public class MovieAlredayExistsException extends Exception {

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MovieAlredayExistsException(final String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MovieAlredayExistsException [message=" + message + "]";
	}

}
