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

import com.ecommerce.ecommerce.admin.dto.CategoryUpsertRequest;
import com.ecommerce.ecommerce.catalog.Category;
import com.ecommerce.ecommerce.catalog.CategoryRepository;
import com.ecommerce.ecommerce.catalog.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminCategoryController {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;

	public AdminCategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	@GetMapping
	public List<Category> list() {
		return categoryRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Category create(@Valid @RequestBody CategoryUpsertRequest body) {
		String slug = normalizeSlug(body.slug());
		if (categoryRepository.existsBySlug(slug)) {
			throw new DuplicateCategorySlugException();
		}
		Category c = new Category();
		apply(c, body, slug, false);
		try {
			return categoryRepository.save(c);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateCategorySlugException();
		}
	}

	@PutMapping("/{id}")
	public Category update(@PathVariable long id, @Valid @RequestBody CategoryUpsertRequest body) {
		Category c = categoryRepository.findById(id).orElseThrow(ContentNotFoundException::new);
		String slug = normalizeSlug(body.slug());
		if (categoryRepository.existsBySlugAndIdNot(slug, id)) {
			throw new DuplicateCategorySlugException();
		}
		apply(c, body, slug, true);
		try {
			return categoryRepository.save(c);
		} catch (DataIntegrityViolationException ex) {
			throw new DuplicateCategorySlugException();
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		if (!categoryRepository.existsById(id)) {
			throw new ContentNotFoundException();
		}
		if (productRepository.countByCategoryId(id) > 0) {
			throw new CategoryInUseException();
		}
		categoryRepository.deleteById(id);
	}

	private static String normalizeSlug(String raw) {
		return raw.trim().toLowerCase().replace(' ', '-');
	}

	private static void apply(Category c, CategoryUpsertRequest r, String slug, boolean mergeUpdate) {
		c.setSlug(slug);
		c.setTitleAr(r.titleAr().trim());
		c.setTitleEn(r.titleEn().trim());
		if (!mergeUpdate || r.sortOrder() != null) {
			c.setSortOrder(r.sortOrder() != null ? r.sortOrder() : 0);
		}
		if (!mergeUpdate || r.active() != null) {
			c.setActive(r.active() == null || Boolean.TRUE.equals(r.active()));
		}
	}
}
