package com.ecommerce.ecommerce.auth.dto;

import com.ecommerce.ecommerce.web.Bilingual;

public record ResetPasswordResponse(int status, String message) {

	public static ResetPasswordResponse of(int status, String bilingual) {
		return new ResetPasswordResponse(status, Bilingual.resolveForRequest(bilingual));
	}
}
