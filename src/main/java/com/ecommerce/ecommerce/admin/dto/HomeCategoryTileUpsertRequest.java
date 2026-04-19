package com.ecommerce.ecommerce.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HomeCategoryTileUpsertRequest(
		@NotBlank String imageUrl,
		@NotBlank String titleAr,
		@NotBlank String titleEn,
		@NotNull Integer sortOrder,
		@NotNull Boolean active) {
}
