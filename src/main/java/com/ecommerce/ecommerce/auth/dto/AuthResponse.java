package com.ecommerce.ecommerce.auth.dto;

import com.ecommerce.ecommerce.web.Bilingual;

public record AuthResponse(int status, String message, String fullName, String token, String email) {

	public static AuthResponse of(int status, String bilingual, String fullName, String token, String email) {
		return new AuthResponse(status, Bilingual.resolveForRequest(bilingual), fullName, token, email);
	}
}
