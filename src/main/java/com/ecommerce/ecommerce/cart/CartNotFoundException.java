package com.ecommerce.ecommerce.cart;

public class CartNotFoundException extends RuntimeException {
	public CartNotFoundException() {
		super("Cart not found");
	}
}
