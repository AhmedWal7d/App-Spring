package com.ecommerce.ecommerce.web.dto;

import java.math.BigDecimal;

public record FavoriteItemDto(
		long favoriteId,
		long productId,
		Long categoryId,
		String categoryTitleAr,
		String categoryTitleEn,
		String sku,
		String titleAr,
		String titleEn,
		String imageUrl,
		BigDecimal currentPrice,
		String currencyAr,
		String currencyEn,
		String productHref) {
}
