package com.ecommerce.ecommerce.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.cart.CartService;
import com.ecommerce.ecommerce.cart.EmptyCartCheckoutException;
import com.ecommerce.ecommerce.web.dto.CartResponse;
import com.ecommerce.ecommerce.web.dto.CartWithCheckoutResponse;
import com.ecommerce.ecommerce.web.dto.CheckoutSessionResponse;
import com.ecommerce.ecommerce.web.dto.CreatePaymentIntentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripePaymentService {

	private final CartService cartService;
	private final String publishableKey;
	private final String checkoutBaseUrl;

	public StripePaymentService(
			CartService cartService,
			@Value("${stripe.secret-key:}") String secretKey,
			@Value("${stripe.publishable-key:}") String publishableKey,
			@Value("${app.checkout.base-url:http://localhost:8080}") String checkoutBaseUrl) {
		this.cartService = cartService;
		this.publishableKey = publishableKey;
		this.checkoutBaseUrl = checkoutBaseUrl.endsWith("/") ? checkoutBaseUrl.substring(0, checkoutBaseUrl.length() - 1) : checkoutBaseUrl;
		if (secretKey != null && !secretKey.isBlank()) {
			Stripe.apiKey = secretKey.trim();
		}
	}

	/**
	 * نفس مسار الدفع الموصى به: صفحة Stripe Checkout (رابط) بقيمة السلة — لا يعتمد على Stripe.js فقط.
	 */
	public CreatePaymentIntentResponse createPaymentIntentForCart(String userEmail) throws StripeException {
		if (Stripe.apiKey == null || Stripe.apiKey.isBlank()) {
			throw new StripeNotConfiguredException();
		}
		CartResponse cart = cartService.getCart(userEmail);
		if (cart.lines().isEmpty() || cart.grandTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new EmptyCartCheckoutException();
		}
		CheckoutSessionResponse checkout = buildStripeCheckoutSession(cart, null, null);
		return new CreatePaymentIntentResponse(
				null,
				publishableKey,
				checkout.amountMinor(),
				checkout.currency(),
				checkout.url(),
				checkout.sessionId());
	}

	/**
	 * صفحة دفع مستضافة من Stripe — يعيد رابطاً يفتح في المتصفح (Checkout).
	 */
	public CheckoutSessionResponse createCheckoutSessionForCart(String userEmail, String successUrlOverride, String cancelUrlOverride)
			throws StripeException {
		if (Stripe.apiKey == null || Stripe.apiKey.isBlank()) {
			throw new StripeNotConfiguredException();
		}
		CartResponse cart = cartService.getCart(userEmail);
		if (cart.lines().isEmpty() || cart.grandTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new EmptyCartCheckoutException();
		}
		return buildStripeCheckoutSession(cart, successUrlOverride, cancelUrlOverride);
	}

	/**
	 * نفس السلة المعروضة في الرد + رابط الدفع بمبلغ {@code cart.grandTotal} (اختبار Stripe).
	 */
	public CartWithCheckoutResponse createCartWithCheckoutSession(String userEmail, String successUrlOverride, String cancelUrlOverride)
			throws StripeException {
		if (Stripe.apiKey == null || Stripe.apiKey.isBlank()) {
			throw new StripeNotConfiguredException();
		}
		CartResponse cart = cartService.getCart(userEmail);
		if (cart.lines().isEmpty() || cart.grandTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new EmptyCartCheckoutException();
		}
		CheckoutSessionResponse checkout = buildStripeCheckoutSession(cart, successUrlOverride, cancelUrlOverride);
		return new CartWithCheckoutResponse(cart, checkout, publishableKey);
	}

	private CheckoutSessionResponse buildStripeCheckoutSession(
			CartResponse cart,
			String successUrlOverride,
			String cancelUrlOverride) throws StripeException {
		long amountMinor = toEgpPiastres(cart.grandTotal());
		String successUrl = (successUrlOverride != null && !successUrlOverride.isBlank())
				? successUrlOverride.trim()
				: checkoutBaseUrl + "/api/web/checkout/complete?session_id={CHECKOUT_SESSION_ID}";
		String cancelUrl = (cancelUrlOverride != null && !cancelUrlOverride.isBlank())
				? cancelUrlOverride.trim()
				: checkoutBaseUrl + "/api/web/checkout/cancel";
		SessionCreateParams params = SessionCreateParams.builder()
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl(successUrl)
				.setCancelUrl(cancelUrl)
				.addLineItem(SessionCreateParams.LineItem.builder()
						.setQuantity(1L)
						.setPriceData(SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency("egp")
								.setUnitAmount(amountMinor)
								.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName("Ecommerce order")
										.build())
								.build())
						.build())
				.putMetadata("cart_id", String.valueOf(cart.cartId()))
				.build();
		Session session = Session.create(params);
		return new CheckoutSessionResponse(session.getUrl(), session.getId(), amountMinor, "egp");
	}

	private static long toEgpPiastres(BigDecimal egp) {
		return egp.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValueExact();
	}
}
