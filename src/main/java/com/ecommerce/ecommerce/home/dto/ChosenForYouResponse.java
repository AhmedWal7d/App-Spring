package com.ecommerce.ecommerce.home.dto;

import java.util.List;

/**
 * قسم «اخترنا لك»: عنوان القسم، زر استكشاف، وقائمة المنتجات (عربي/إنجليزي لكل نص).
 */
public record ChosenForYouResponse(
		String sectionTitleAr,
		String sectionTitleEn,
		String ctaLabelAr,
		String ctaLabelEn,
		String ctaHref,
		List<ChosenForYouProductDto> products) {
}
