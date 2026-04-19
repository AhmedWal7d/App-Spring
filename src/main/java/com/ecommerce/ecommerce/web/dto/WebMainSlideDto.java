package com.ecommerce.ecommerce.web.dto;

public record WebMainSlideDto(
		long id,
		String imageUrl,
		String titleAr,
		String titleEn,
		String subtitleAr,
		String subtitleEn,
		String ctaLabelAr,
		String ctaLabelEn,
		String ctaHref) {
}
