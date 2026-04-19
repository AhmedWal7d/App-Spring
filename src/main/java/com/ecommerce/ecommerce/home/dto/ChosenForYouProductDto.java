package com.ecommerce.ecommerce.home.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * بطاقة منتج في شريط «اخترنا لك» — كل النصوص عربي + إنجليزي.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChosenForYouProductDto(
		long id,
		Long categoryId,
		String categoryTitleAr,
		String categoryTitleEn,
		String productHref,
		boolean wishlisted,
		String categoryBadgeAr,
		String categoryBadgeEn,
		String specialBadgeAr,
		String specialBadgeEn,
		String imageUrl,
		double rating,
		String titleAr,
		String titleEn,
		BigDecimal currentPrice,
		BigDecimal originalPrice,
		String currencyAr,
		String currencyEn,
		String discountPercentAr,
		String discountPercentEn,
		String installmentSummaryAr,
		String installmentSummaryEn,
		String promoFooterAr,
		String promoFooterEn) {
}
