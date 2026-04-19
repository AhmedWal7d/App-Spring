package com.ecommerce.ecommerce.admin;

public class ContentNotFoundException extends RuntimeException {
	public ContentNotFoundException() {
		super("Content not found");
	}
}
