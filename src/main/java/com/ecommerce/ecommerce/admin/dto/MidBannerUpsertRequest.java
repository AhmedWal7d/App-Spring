package com.ecommerce.ecommerce.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MidBannerUpsertRequest(
		@NotBlank String imageUrl,
		String titleAr,
		String titleEn,
		String linkHref,
		@NotNull Integer sortOrder,
		@NotNull Boolean active) {
}
