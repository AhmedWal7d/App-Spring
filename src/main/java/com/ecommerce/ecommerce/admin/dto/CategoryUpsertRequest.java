package com.ecommerce.ecommerce.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpsertRequest(
		@NotBlank String slug,
		@NotBlank String titleAr,
		@NotBlank String titleEn,
		@NotNull Integer sortOrder,
		Boolean active) {
}
