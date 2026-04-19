package com.ecommerce.ecommerce.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.home.dto.ChosenForYouProductDto;
import com.ecommerce.ecommerce.web.dto.WebStoreCategoryDto;

@RestController
@RequestMapping(value = "/api/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebCatalogController {

	private final WebCatalogService webCatalogService;

	public WebCatalogController(WebCatalogService webCatalogService) {
		this.webCatalogService = webCatalogService;
	}

	/** التصنيفات النشطة للمتجر (تصفية المنتجات). */
	@GetMapping("/categories")
	public List<WebStoreCategoryDto> categories() {
		return webCatalogService.listActiveCategories();
	}

	/** كل المنتجات النشطة، أو تصفية حسب التصنيف عند تمرير {@code categoryId}. */
	@GetMapping("/products")
	public List<ChosenForYouProductDto> products(@RequestParam(required = false) Long categoryId) {
		return webCatalogService.listActiveProducts(categoryId);
	}
}
