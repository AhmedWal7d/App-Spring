package com.ecommerce.ecommerce.auth.dto;

import com.ecommerce.ecommerce.web.Bilingual;

public record UserProfileResponse(int status, String message, String email, String fullName, String phone) {

	public static UserProfileResponse of(int status, String bilingual, String email, String fullName, String phone) {
		return new UserProfileResponse(status, Bilingual.resolveForRequest(bilingual), email, fullName, phone);
	}
}
