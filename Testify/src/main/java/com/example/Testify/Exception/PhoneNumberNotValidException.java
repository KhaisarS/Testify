package com.example.Testify.Exception;

public class PhoneNumberNotValidException extends RuntimeException{
	String message="enter valid mobile number";

	
	public PhoneNumberNotValidException() {

	}

	public PhoneNumberNotValidException(String message) {
		super();
		this.message = message;
	}


	@Override
	public String getMessage() {
		return message;
	}
	
}
