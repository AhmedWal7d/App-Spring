package com.ecommerce.ecommerce.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.checkout.StripePaymentService;
import com.ecommerce.ecommerce.web.dto.CartWithCheckoutResponse;
import com.ecommerce.ecommerce.web.dto.CheckoutReturnResponse;
import com.ecommerce.ecommerce.web.dto.CheckoutSessionResponse;
import com.ecommerce.ecommerce.web.dto.CreateCheckoutSessionRequest;
import com.ecommerce.ecommerce.web.dto.CreatePaymentIntentResponse;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping(value = "/api/web/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebCheckoutController {

	private final StripePaymentService stripePaymentService;

	public WebCheckoutController(StripePaymentService stripePaymentService) {
		this.stripePaymentService = stripePaymentService;
	}

	/**
	 * السلة كاملة (أسطر + إجمالي) + رابط دفع Stripe test بنفس المبلغ — طلب واحد.
	 * جسم اختياري: {@code { "successUrl": "...", "cancelUrl": "..." }} أو {@code {}}.
	 */
	@PostMapping("/cart-and-session")
	public CartWithCheckoutResponse cartAndCheckoutSession(
			@AuthenticationPrincipal UserDetails principal,
			@RequestBody(required = false) CreateCheckoutSessionRequest body) throws StripeException {
		String success = body != null ? body.successUrl() : null;
		String cancel = body != null ? body.cancelUrl() : null;
		return stripePaymentService.createCartWithCheckoutSession(principal.getUsername(), success, cancel);
	}

	/**
	 * دفع عبر صفحة Stripe المستضافة — الاستجابة تحتوي {@code url} فقط (بدون تفاصيل السلة).
	 * جسم الطلب اختياري: {@code { "successUrl": "...", "cancelUrl": "..." }} أو {@code {}}.
	 */
	@PostMapping("/session")
	public CheckoutSessionResponse createCheckoutSession(
			@AuthenticationPrincipal UserDetails principal,
			@RequestBody(required = false) CreateCheckoutSessionRequest body) throws StripeException {
		String success = body != null ? body.successUrl() : null;
		String cancel = body != null ? body.cancelUrl() : null;
		return stripePaymentService.createCheckoutSessionForCart(principal.getUsername(), success, cancel);
	}

	/** يعيد رابط دفع Stripe Checkout في {@code hostedCheckoutUrl} (نفس مبلغ السلة). */
	@PostMapping("/payment-intent")
	public CreatePaymentIntentResponse createPaymentIntent(@AuthenticationPrincipal UserDetails principal) throws StripeException {
		return stripePaymentService.createPaymentIntentForCart(principal.getUsername());
	}

	/** يستدعيه Stripe بعد نجاح الدفع (يفتحه المتصفح — لا يحتاج JWT). */
	@GetMapping("/complete")
	public CheckoutReturnResponse checkoutComplete(@RequestParam(value = "session_id", required = false) String sessionId) {
		return new CheckoutReturnResponse(
				"paid",
				sessionId,
				"تم إكمال الدفع (اختبار). راجع Stripe Dashboard → Payments.");
	}

	/** يستدعيه المتصفح عند إلغاء Checkout. */
	@GetMapping("/cancel")
	public CheckoutReturnResponse checkoutCancel() {
		return new CheckoutReturnResponse("cancelled", null, "تم إلغاء الدفع من صفحة Stripe.");
	}
}
