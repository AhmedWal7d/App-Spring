package com.ecommerce.ecommerce.auth;

public class InvalidLoginException extends RuntimeException {

	public InvalidLoginException() {
		super("Invalid email or password");
	}
}
