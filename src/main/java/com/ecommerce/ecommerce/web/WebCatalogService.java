package com.ecommerce.ecommerce.web;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalog.CategoryRepository;
import com.ecommerce.ecommerce.catalog.ProductRepository;
import com.ecommerce.ecommerce.home.HomeMapper;
import com.ecommerce.ecommerce.home.dto.ChosenForYouProductDto;
import com.ecommerce.ecommerce.web.dto.WebStoreCategoryDto;

@Service
public class WebCatalogService {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;

	public WebCatalogService(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	@Transactional(readOnly = true)
	public List<WebStoreCategoryDto> listActiveCategories() {
		return categoryRepository.findByActiveTrueOrderBySortOrderAsc().stream()
				.map(c -> new WebStoreCategoryDto(c.getId(), c.getSlug(), c.getTitleAr(), c.getTitleEn()))
				.toList();
	}

	@Transactional(readOnly = true)
	public List<ChosenForYouProductDto> listActiveProducts(Long categoryId) {
		var list = categoryId != null && categoryId > 0
				? productRepository.findByCategoryIdActiveWithCategory(categoryId)
				: productRepository.findAllActiveWithCategory();
		return list.stream().map(HomeMapper::toChosenProduct).toList();
	}
}
