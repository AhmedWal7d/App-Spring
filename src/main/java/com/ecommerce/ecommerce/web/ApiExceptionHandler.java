package com.ecommerce.ecommerce.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

import com.ecommerce.ecommerce.admin.CategoryInUseException;
import com.ecommerce.ecommerce.admin.ContentNotFoundException;
import com.ecommerce.ecommerce.admin.DuplicateCategorySlugException;
import com.ecommerce.ecommerce.auth.EmailAlreadyRegisteredException;
import com.ecommerce.ecommerce.auth.InvalidLoginException;
import com.ecommerce.ecommerce.auth.InvalidResetTokenException;
import com.ecommerce.ecommerce.cart.CartItemNotFoundException;
import com.ecommerce.ecommerce.cart.CartNotFoundException;
import com.ecommerce.ecommerce.cart.EmptyCartCheckoutException;
import com.ecommerce.ecommerce.catalog.CategoryNotFoundException;
import com.ecommerce.ecommerce.catalog.DuplicateSkuException;
import com.ecommerce.ecommerce.catalog.ProductNotFoundException;
import com.ecommerce.ecommerce.home.MaxActiveMidBannersException;
import com.ecommerce.ecommerce.checkout.StripeNotConfiguredException;
import com.ecommerce.ecommerce.web.dto.ApiErrorResponse;
import com.stripe.exception.StripeException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		List<String> details = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage())
				.collect(Collectors.toList());
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "validation_failed", Messages.VALIDATION, request, details));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "bad_json", Messages.BAD_JSON, request, null));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiErrorResponse> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest request) {
		String detail = ex.getParameterName();
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(
						HttpStatus.BAD_REQUEST.value(),
						"missing_parameter",
						Messages.MISSING_PARAM,
						request,
						List.of(detail)));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(ApiErrorResponse.of(
						HttpStatus.METHOD_NOT_ALLOWED.value(),
						"method_not_allowed",
						Messages.METHOD_NOT_ALLOWED,
						request,
						List.of(ex.getMethod())));
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(NoResourceFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "not_found", Messages.NOT_FOUND, request, null));
	}

	@ExceptionHandler(EmailAlreadyRegisteredException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateEmail(EmailAlreadyRegisteredException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(HttpStatus.CONFLICT.value(), "email_taken", Messages.EMAIL_TAKEN, request, null));
	}

	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidLogin(InvalidLoginException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "invalid_credentials", Messages.LOGIN_FAIL, request, null));
	}

	@ExceptionHandler(InvalidResetTokenException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidResetToken(InvalidResetTokenException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "invalid_or_expired_otp", Messages.OTP_INVALID, request, null));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ApiErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), "unauthorized", Messages.UNAUTHORIZED, request, null));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ApiErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(ApiErrorResponse.of(
						HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
						"unsupported_media_type",
						Messages.UNSUPPORTED_MEDIA,
						request,
						null));
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "product_not_found", Messages.PRODUCT_NOT_FOUND, request, null));
	}

	@ExceptionHandler(DuplicateSkuException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateSku(DuplicateSkuException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(HttpStatus.CONFLICT.value(), "sku_taken", Messages.SKU_TAKEN, request, null));
	}

	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleCartNotFound(CartNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "cart_not_found", Messages.CART_NOT_FOUND, request, null));
	}

	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleCartItemNotFound(CartItemNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "cart_item_not_found", Messages.CART_ITEM_NOT_FOUND, request, null));
	}

	@ExceptionHandler(MaxActiveMidBannersException.class)
	public ResponseEntity<ApiErrorResponse> handleMaxMidBanners(MaxActiveMidBannersException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(HttpStatus.CONFLICT.value(), "max_mid_banners", Messages.MAX_MID_BANNERS, request, null));
	}

	@ExceptionHandler(ContentNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleContentNotFound(ContentNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "not_found", Messages.ADMIN_RESOURCE_NOT_FOUND, request, null));
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), "category_not_found", Messages.CATEGORY_NOT_FOUND, request, null));
	}

	@ExceptionHandler(DuplicateCategorySlugException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateCategorySlug(DuplicateCategorySlugException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(HttpStatus.CONFLICT.value(), "category_slug_taken", Messages.CATEGORY_SLUG_TAKEN, request, null));
	}

	@ExceptionHandler(CategoryInUseException.class)
	public ResponseEntity<ApiErrorResponse> handleCategoryInUse(CategoryInUseException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(HttpStatus.CONFLICT.value(), "category_in_use", Messages.CATEGORY_IN_USE, request, null));
	}

	@ExceptionHandler(EmptyCartCheckoutException.class)
	public ResponseEntity<ApiErrorResponse> handleEmptyCartCheckout(EmptyCartCheckoutException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "cart_empty_checkout", Messages.CART_EMPTY_CHECKOUT, request, null));
	}

	@ExceptionHandler(StripeException.class)
	public ResponseEntity<ApiErrorResponse> handleStripe(StripeException ex, HttpServletRequest request) {
		String detail = ex.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(ApiErrorResponse.of(
						HttpStatus.BAD_GATEWAY.value(),
						"stripe_error",
						Messages.STRIPE_ERROR,
						request,
						detail != null ? List.of(detail) : null));
	}

	@ExceptionHandler(StripeNotConfiguredException.class)
	public ResponseEntity<ApiErrorResponse> handleStripeNotConfigured(StripeNotConfiguredException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(ApiErrorResponse.of(
						HttpStatus.SERVICE_UNAVAILABLE.value(),
						"stripe_not_configured",
						Messages.STRIPE_NOT_CONFIGURED,
						request,
						null));
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiErrorResponse> handleDataAccess(DataAccessException ex, HttpServletRequest request) {
		String detail = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
		String safe = detail == null ? "" : (detail.length() > 300 ? detail.substring(0, 300) + "..." : detail);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiErrorResponse.of(
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						"database_error",
						Messages.DATABASE,
						request,
						List.of(safe)));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiErrorResponse.of(
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						"internal_error",
						Messages.INTERNAL,
						request,
						List.of(ex.getClass().getSimpleName())));
	}
}
