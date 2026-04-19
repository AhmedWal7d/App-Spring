package com.ecommerce.ecommerce.admin.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Admin create/update body. Only {@code sku}, {@code imageUrl}, titles, and {@code currentPrice} are required;
 * everything else is optional (defaults apply on create; on update, omitted/null optional fields keep existing values).
 */
public record ProductUpsertRequest(
		@NotBlank String sku,
		Long categoryId,
		String productHref,
		@NotBlank String imageUrl,
		Double rating,
		@NotBlank String titleAr,
		@NotBlank String titleEn,
		@NotNull BigDecimal currentPrice,
		BigDecimal originalPrice,
		String currencyAr,
		String currencyEn,
		String discountPercentAr,
		String discountPercentEn,
		String installmentSummaryAr,
		String installmentSummaryEn,
		String promoFooterAr,
		String promoFooterEn,
		String categoryBadgeAr,
		String categoryBadgeEn,
		String specialBadgeAr,
		String specialBadgeEn,
		Boolean featuredOnHome,
		Integer homeSortOrder,
		Boolean active) {
}
