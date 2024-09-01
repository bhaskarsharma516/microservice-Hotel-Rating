package com.pro.user.service.exceptions;

public class UsernameAlreadyExistException extends RuntimeException {
	public UsernameAlreadyExistException() {
		super("Username already exist.Try with different username ");
	}

	public UsernameAlreadyExistException(String message) {
		super(message);
	}


}
