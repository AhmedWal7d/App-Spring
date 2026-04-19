package com.ecommerce.ecommerce.auth;

public class InvalidResetTokenException extends RuntimeException {

	public InvalidResetTokenException() {
		super("Invalid or expired OTP. Use the 6 digits from the latest email, same address as forgot-password, before it expires.");
	}
}
