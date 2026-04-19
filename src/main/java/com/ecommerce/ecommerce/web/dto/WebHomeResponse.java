package com.ecommerce.ecommerce.web.dto;

import java.util.List;

import com.ecommerce.ecommerce.home.dto.ChosenForYouResponse;

/** تجميع كل محتوى الصفحة الرئيسية للويب. */
public record WebHomeResponse(
		List<WebMainSlideDto> mainSlider,
		List<WebCategoryTileDto> categorySlider,
		ChosenForYouResponse chosenForYou,
		List<WebMidBannerDto> midBanners) {
}
