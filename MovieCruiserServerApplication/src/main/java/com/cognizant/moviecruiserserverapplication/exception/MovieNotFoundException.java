package com.cognizant.moviecruiserserverapplication.exception;

@SuppressWarnings("serial")
public class MovieNotFoundException extends Exception {

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MovieNotFoundException(final String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MovieNotFoundException [message=" + message + "]";
	}

}
