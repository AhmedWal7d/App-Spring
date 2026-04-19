package com.ecommerce.ecommerce.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerce.auth.dto.AuthResponse;
import com.ecommerce.ecommerce.auth.dto.ForgotPasswordRequest;
import com.ecommerce.ecommerce.auth.dto.ForgotPasswordResponse;
import com.ecommerce.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.ecommerce.auth.dto.ResetPasswordRequest;
import com.ecommerce.ecommerce.auth.dto.ResetPasswordResponse;
import com.ecommerce.ecommerce.auth.dto.UserProfileResponse;

import com.ecommerce.ecommerce.web.Bilingual;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final PasswordResetService passwordResetService;

	public AuthController(AuthService authService, PasswordResetService passwordResetService) {
		this.authService = authService;
		this.passwordResetService = passwordResetService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest http) {
		applyRequestLanguageFromBody(http, request.lang());
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest http) {
		applyRequestLanguageFromBody(http, request.lang());
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
		ForgotPasswordResponse body = passwordResetService.forgotPassword(request);
		return ResponseEntity.status(body.status()).body(body);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		ResetPasswordResponse body = passwordResetService.resetPassword(request);
		return ResponseEntity.status(body.status()).body(body);
	}

	@GetMapping("/me")
	public ResponseEntity<UserProfileResponse> me(@AuthenticationPrincipal UserDetails principal) {
		return ResponseEntity.ok(authService.currentProfile(principal.getUsername()));
	}

	/**
	 * Uses body/query {@code lang} when the URL query {@code lang} / {@code language} is not already set.
	 */
	private static void applyRequestLanguageFromBody(HttpServletRequest http, String langFromBody) {
		if (langFromBody == null || langFromBody.isBlank()) {
			return;
		}
		if (http.getParameter("lang") != null && !http.getParameter("lang").isBlank()) {
			return;
		}
		if (http.getParameter("language") != null && !http.getParameter("language").isBlank()) {
			return;
		}
		http.setAttribute(Bilingual.REQUEST_LANG_ATTR, langFromBody.trim());
	}
}
