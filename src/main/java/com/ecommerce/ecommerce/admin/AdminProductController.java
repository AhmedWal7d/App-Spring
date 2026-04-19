package com.ecommerce.ecommerce.admin;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.admin.dto.ProductUpsertRequest;
import com.ecommerce.ecommerce.catalog.CategoryNotFoundException;
import com.ecommerce.ecommerce.catalog.CategoryRepository;
import com.ecommerce.ecommerce.catalog.DuplicateSkuException;
import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.catalog.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminProductController {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public AdminProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping
	public List<Product> list() {
		return productRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@Valid @RequestBody ProductUpsertRequest body) {
		String sku = body.sku().trim();
		if (productRepository.existsBySku(sku)) {
			throw new DuplicateSkuException();
		}
		Product p = new Product();
		apply(p, body, false);
		try {
			return productRepository.save(p);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateSkuException();
		}
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable long id, @Valid @RequestBody ProductUpsertRequest body) {
		Product p = productRepository.findById(id).orElseThrow(ContentNotFoundException::new);
		String sku = body.sku().trim();
		if (productRepository.existsBySkuAndIdNot(sku, id)) {
			throw new DuplicateSkuException();
		}
		apply(p, body, true);
		try {
			return productRepository.save(p);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateSkuException();
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		if (!productRepository.existsById(id)) {
			throw new ContentNotFoundException();
		}
		productRepository.deleteById(id);
	}

	/**
	 * @param mergeUpdate when {@code true} (PUT), optional null fields keep existing values; when {@code false} (POST), nulls use create defaults.
	 */
	private void apply(Product p, ProductUpsertRequest r, boolean mergeUpdate) {
		p.setSku(r.sku().trim());
		if (!mergeUpdate) {
			if (r.categoryId() == null || r.categoryId() <= 0) {
				throw new CategoryNotFoundException();
			}
			p.setCategory(categoryRepository.findById(r.categoryId()).orElseThrow(CategoryNotFoundException::new));
		}
		else if (r.categoryId() != null) {
			if (r.categoryId() <= 0) {
				throw new CategoryNotFoundException();
			}
			p.setCategory(categoryRepository.findById(r.categoryId()).orElseThrow(CategoryNotFoundException::new));
		}
		p.setImageUrl(r.imageUrl().trim());
		p.setTitleAr(r.titleAr().trim());
		p.setTitleEn(r.titleEn().trim());
		p.setCurrentPrice(r.currentPrice());

		if (!mergeUpdate || r.productHref() != null) {
			p.setProductHref(trimNull(r.productHref()));
		}
		if (!mergeUpdate || r.rating() != null) {
			p.setRating(r.rating() != null ? r.rating() : 5.0);
		}
		if (!mergeUpdate || r.originalPrice() != null) {
			p.setOriginalPrice(r.originalPrice());
		}
		if (!mergeUpdate || r.currencyAr() != null) {
			p.setCurrencyAr(r.currencyAr() != null && !r.currencyAr().isBlank() ? r.currencyAr().trim() : "ج.م");
		}
		if (!mergeUpdate || r.currencyEn() != null) {
			p.setCurrencyEn(r.currencyEn() != null && !r.currencyEn().isBlank() ? r.currencyEn().trim() : "EGP");
		}
		if (!mergeUpdate || r.discountPercentAr() != null) {
			p.setDiscountPercentAr(trimNull(r.discountPercentAr()));
		}
		if (!mergeUpdate || r.discountPercentEn() != null) {
			p.setDiscountPercentEn(trimNull(r.discountPercentEn()));
		}
		if (!mergeUpdate || r.installmentSummaryAr() != null) {
			p.setInstallmentSummaryAr(trimNull(r.installmentSummaryAr()));
		}
		if (!mergeUpdate || r.installmentSummaryEn() != null) {
			p.setInstallmentSummaryEn(trimNull(r.installmentSummaryEn()));
		}
		if (!mergeUpdate || r.promoFooterAr() != null) {
			p.setPromoFooterAr(trimNull(r.promoFooterAr()));
		}
		if (!mergeUpdate || r.promoFooterEn() != null) {
			p.setPromoFooterEn(trimNull(r.promoFooterEn()));
		}
		if (!mergeUpdate || r.categoryBadgeAr() != null) {
			p.setCategoryBadgeAr(trimNull(r.categoryBadgeAr()));
		}
		if (!mergeUpdate || r.categoryBadgeEn() != null) {
			p.setCategoryBadgeEn(trimNull(r.categoryBadgeEn()));
		}
		if (!mergeUpdate || r.specialBadgeAr() != null) {
			p.setSpecialBadgeAr(trimNull(r.specialBadgeAr()));
		}
		if (!mergeUpdate || r.specialBadgeEn() != null) {
			p.setSpecialBadgeEn(trimNull(r.specialBadgeEn()));
		}
		if (!mergeUpdate || r.featuredOnHome() != null) {
			p.setFeaturedOnHome(Boolean.TRUE.equals(r.featuredOnHome()));
		}
		if (!mergeUpdate || r.homeSortOrder() != null) {
			p.setHomeSortOrder(r.homeSortOrder() != null ? r.homeSortOrder() : 0);
		}
		if (!mergeUpdate || r.active() != null) {
			p.setActive(r.active() == null || Boolean.TRUE.equals(r.active()));
		}
	}

	private static String trimNull(String s) {
		if (s == null) {
			return null;
		}
		String t = s.trim();
		return t.isEmpty() ? null : t;
	}
}
