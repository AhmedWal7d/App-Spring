package com.ecommerce.ecommerce.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HomeMainSlideUpsertRequest(
		@NotBlank String imageUrl,
		@NotBlank String titleAr,
		@NotBlank String titleEn,
		String subtitleAr,
		String subtitleEn,
		String ctaLabelAr,
		String ctaLabelEn,
		String ctaHref,
		@NotNull Integer sortOrder,
		@NotNull Boolean active) {
}
