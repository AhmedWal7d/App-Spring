package com.ecommerce.ecommerce.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
		@JsonProperty("email")
		@JsonAlias({ "Email", "userEmail", "mail" })
		@NotBlank @Size(max = 255) String email,
		@JsonProperty("password")
		@JsonAlias({ "Password", "pass", "pwd" })
		@NotBlank @Size(max = 128) String password,
		/** Response language: {@code en} or {@code ar} (optional; query {@code ?lang=} overrides). */
		@JsonProperty("lang")
		@JsonAlias({ "locale", "language" })
		@Size(max = 16) String lang) {

	public LoginRequest {
		lang = lang == null || lang.isBlank() ? null : lang.trim();
	}
}
