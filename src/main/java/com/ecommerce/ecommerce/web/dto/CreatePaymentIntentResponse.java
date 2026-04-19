package com.ecommerce.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * دفع السلة عبر Stripe: استخدم {@code hostedCheckoutUrl} في المتصفح. {@code clientSecret} يُترك فارغاً في هذا المسار.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePaymentIntentResponse(
		@JsonInclude(JsonInclude.Include.NON_NULL) String clientSecret,
		String publishableKey,
		long amountMinor,
		String currency,
		String hostedCheckoutUrl,
		String sessionId) {
}
