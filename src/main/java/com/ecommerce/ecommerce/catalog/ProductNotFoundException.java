package com.ecommerce.ecommerce.catalog;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException() {
		super("Product not found");
	}
}
