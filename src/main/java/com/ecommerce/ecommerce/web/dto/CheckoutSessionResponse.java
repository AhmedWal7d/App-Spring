package com.ecommerce.ecommerce.web.dto;

/** رابط صفحة الدفع المستضافة (Stripe Checkout). افتح {@code url} في المتصفح. */
public record CheckoutSessionResponse(
		String url,
		String sessionId,
		long amountMinor,
		String currency) {
}
