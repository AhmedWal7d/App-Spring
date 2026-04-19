package com.ecommerce.ecommerce.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ecommerce.ecommerce.web.Bilingual;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ForgotPasswordResponse(int status, String message, String error, String smtpDetail) {

	public static ForgotPasswordResponse of(int status, String bilingual, String error, String smtpDetail) {
		return new ForgotPasswordResponse(status, Bilingual.resolveForRequest(bilingual), error, smtpDetail);
	}
}
