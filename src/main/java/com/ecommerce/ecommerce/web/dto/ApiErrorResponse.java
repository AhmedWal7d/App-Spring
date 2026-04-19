package com.ecommerce.ecommerce.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ecommerce.ecommerce.web.Bilingual;

import jakarta.servlet.http.HttpServletRequest;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(int status, String error, String message, List<String> details) {

	public static ApiErrorResponse of(int status, String error, String bilingualMessage) {
		return of(status, error, bilingualMessage, null);
	}

	public static ApiErrorResponse of(int status, String error, String bilingualMessage, List<String> details) {
		return new ApiErrorResponse(
				status,
				error,
				Bilingual.resolveForRequest(bilingualMessage),
				details);
	}

	public static ApiErrorResponse of(int status, String error, String bilingualMessage, HttpServletRequest request, List<String> details) {
		return new ApiErrorResponse(
				status,
				error,
				Bilingual.pickForRequest(bilingualMessage, request),
				details);
	}
}
