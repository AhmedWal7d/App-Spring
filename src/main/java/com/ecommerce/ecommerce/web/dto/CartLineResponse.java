package com.ecommerce.ecommerce.web.dto;

import java.math.BigDecimal;

public record CartLineResponse(
		long lineId,
		long productId,
		Long categoryId,
		String categoryTitleAr,
		String categoryTitleEn,
		String sku,
		String titleAr,
		String titleEn,
		String imageUrl,
		BigDecimal unitPrice,
		String currencyAr,
		String currencyEn,
		int quantity,
		BigDecimal lineTotal) {
}
