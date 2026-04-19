package com.ecommerce.ecommerce.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalog.ProductRepository;
import com.ecommerce.ecommerce.home.dto.ChosenForYouResponse;
import com.ecommerce.ecommerce.home.repository.HomeCategoryTileRepository;
import com.ecommerce.ecommerce.home.repository.HomeMainSlideRepository;
import com.ecommerce.ecommerce.home.repository.MidBannerRepository;
import com.ecommerce.ecommerce.web.dto.WebHomeResponse;
import com.ecommerce.ecommerce.web.dto.WebMidBannerDto;

@Service
public class HomeReadService {

	private final HomeMainSlideRepository mainSlideRepository;
	private final HomeCategoryTileRepository categoryTileRepository;
	private final MidBannerRepository midBannerRepository;
	private final ProductRepository productRepository;

	private final String chosenTitleAr;
	private final String chosenTitleEn;
	private final String chosenCtaAr;
	private final String chosenCtaEn;
	private final String chosenCtaHref;

	public HomeReadService(
			HomeMainSlideRepository mainSlideRepository,
			HomeCategoryTileRepository categoryTileRepository,
			MidBannerRepository midBannerRepository,
			ProductRepository productRepository,
			@Value("${app.home.chosen-title-ar}") String chosenTitleAr,
			@Value("${app.home.chosen-title-en}") String chosenTitleEn,
			@Value("${app.home.chosen-cta-ar}") String chosenCtaAr,
			@Value("${app.home.chosen-cta-en}") String chosenCtaEn,
			@Value("${app.home.chosen-cta-href}") String chosenCtaHref) {
		this.mainSlideRepository = mainSlideRepository;
		this.categoryTileRepository = categoryTileRepository;
		this.midBannerRepository = midBannerRepository;
		this.productRepository = productRepository;
		this.chosenTitleAr = chosenTitleAr;
		this.chosenTitleEn = chosenTitleEn;
		this.chosenCtaAr = chosenCtaAr;
		this.chosenCtaEn = chosenCtaEn;
		this.chosenCtaHref = chosenCtaHref;
	}

	@Transactional(readOnly = true)
	public WebHomeResponse loadWebHome() {
		var main = mainSlideRepository.findByActiveTrueOrderBySortOrderAsc().stream().map(HomeMapper::toWebMain).toList();
		var categories = categoryTileRepository.findByActiveTrueOrderBySortOrderAsc().stream().map(HomeMapper::toWebCategory).toList();
		var products = productRepository.findByFeaturedOnHomeTrueAndActiveTrueOrderByHomeSortOrderAsc().stream()
				.map(HomeMapper::toChosenProduct)
				.toList();
		var chosen = new ChosenForYouResponse(chosenTitleAr, chosenTitleEn, chosenCtaAr, chosenCtaEn, chosenCtaHref, products);
		List<WebMidBannerDto> mids = midBannerRepository.findByActiveTrueOrderBySortOrderAsc().stream()
				.map(HomeMapper::toWebMid)
				.limit(2)
				.toList();
		return new WebHomeResponse(main, categories, chosen, mids);
	}
}
