package com.ecommerce.ecommerce.home;

import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.home.dto.ChosenForYouProductDto;
import com.ecommerce.ecommerce.home.entity.HomeCategoryTile;
import com.ecommerce.ecommerce.home.entity.HomeMainSlide;
import com.ecommerce.ecommerce.home.entity.MidBanner;
import com.ecommerce.ecommerce.web.dto.WebCategoryTileDto;
import com.ecommerce.ecommerce.web.dto.WebMainSlideDto;
import com.ecommerce.ecommerce.web.dto.WebMidBannerDto;

public final class HomeMapper {

	private HomeMapper() {
	}

	public static WebMainSlideDto toWebMain(HomeMainSlide e) {
		return new WebMainSlideDto(
				e.getId(),
				e.getImageUrl(),
				e.getTitleAr(),
				e.getTitleEn(),
				e.getSubtitleAr(),
				e.getSubtitleEn(),
				e.getCtaLabelAr(),
				e.getCtaLabelEn(),
				e.getCtaHref());
	}

	public static WebCategoryTileDto toWebCategory(HomeCategoryTile e) {
		return new WebCategoryTileDto(e.getId(), e.getImageUrl(), e.getTitleAr(), e.getTitleEn());
	}

	public static WebMidBannerDto toWebMid(MidBanner e) {
		return new WebMidBannerDto(e.getId(), e.getImageUrl(), e.getTitleAr(), e.getTitleEn(), e.getLinkHref());
	}

	public static ChosenForYouProductDto toChosenProduct(Product p) {
		var cat = p.getCategory();
		return new ChosenForYouProductDto(
				p.getId(),
				cat != null ? cat.getId() : null,
				cat != null ? cat.getTitleAr() : null,
				cat != null ? cat.getTitleEn() : null,
				p.getProductHref(),
				false,
				p.getCategoryBadgeAr(),
				p.getCategoryBadgeEn(),
				p.getSpecialBadgeAr(),
				p.getSpecialBadgeEn(),
				p.getImageUrl(),
				p.getRating(),
				p.getTitleAr(),
				p.getTitleEn(),
				p.getCurrentPrice(),
				p.getOriginalPrice(),
				p.getCurrencyAr(),
				p.getCurrencyEn(),
				p.getDiscountPercentAr(),
				p.getDiscountPercentEn(),
				p.getInstallmentSummaryAr(),
				p.getInstallmentSummaryEn(),
				p.getPromoFooterAr(),
				p.getPromoFooterEn());
	}
}
