package com.ecommerce.ecommerce.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
		@NotBlank @Size(max = 255) String email,
		@NotBlank @Size(min = 6, max = 64) String otp,
		@NotBlank @Size(min = 8, max = 128) String newPassword) {
}
