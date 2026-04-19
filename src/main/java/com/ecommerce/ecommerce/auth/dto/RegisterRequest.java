package com.ecommerce.ecommerce.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(
		@NotBlank @Size(max = 100) String firstName,
		@NotBlank @Size(max = 100) String lastName,
		@NotBlank @Email @Size(max = 255) String email,
		@NotBlank @Size(min = 8, max = 128) String password,
		@NotBlank @Size(max = 30) String phone,
		/** Response language: {@code en} or {@code ar} (optional; query {@code ?lang=} overrides). */
		@JsonProperty("lang")
		@Size(max = 16) String lang) {

	public RegisterRequest {
		lang = lang == null || lang.isBlank() ? null : lang.trim();
	}
}
