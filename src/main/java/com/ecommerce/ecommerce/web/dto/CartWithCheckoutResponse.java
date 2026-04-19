package com.ecommerce.ecommerce.web.dto;

/**
 * لقطة السلة كاملة + رابط دفع Stripe (نفس إجمالي السلة).
 */
public record CartWithCheckoutResponse(
		CartResponse cart,
		CheckoutSessionResponse checkout,
		String publishableKey) {
}
