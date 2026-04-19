package com.ecommerce.ecommerce.cart;

public class CartItemNotFoundException extends RuntimeException {
	public CartItemNotFoundException() {
		super("Cart line not found");
	}
}
