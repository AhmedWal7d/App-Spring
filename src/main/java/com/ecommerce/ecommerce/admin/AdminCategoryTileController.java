package com.ecommerce.ecommerce.admin;

import java.util.List;

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

import com.ecommerce.ecommerce.admin.dto.HomeCategoryTileUpsertRequest;
import com.ecommerce.ecommerce.home.entity.HomeCategoryTile;
import com.ecommerce.ecommerce.home.repository.HomeCategoryTileRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin/home/category-tiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminCategoryTileController {

	private final HomeCategoryTileRepository homeCategoryTileRepository;

	public AdminCategoryTileController(HomeCategoryTileRepository homeCategoryTileRepository) {
		this.homeCategoryTileRepository = homeCategoryTileRepository;
	}

	@GetMapping
	public List<HomeCategoryTile> list() {
		return homeCategoryTileRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public HomeCategoryTile create(@Valid @RequestBody HomeCategoryTileUpsertRequest body) {
		HomeCategoryTile e = new HomeCategoryTile();
		apply(e, body);
		return homeCategoryTileRepository.save(e);
	}

	@PutMapping("/{id}")
	public HomeCategoryTile update(@PathVariable long id, @Valid @RequestBody HomeCategoryTileUpsertRequest body) {
		HomeCategoryTile e = homeCategoryTileRepository.findById(id).orElseThrow(ContentNotFoundException::new);
		apply(e, body);
		return homeCategoryTileRepository.save(e);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		if (!homeCategoryTileRepository.existsById(id)) {
			throw new ContentNotFoundException();
		}
		homeCategoryTileRepository.deleteById(id);
	}

	private static void apply(HomeCategoryTile e, HomeCategoryTileUpsertRequest r) {
		e.setImageUrl(r.imageUrl().trim());
		e.setTitleAr(r.titleAr().trim());
		e.setTitleEn(r.titleEn().trim());
		e.setSortOrder(r.sortOrder());
		e.setActive(Boolean.TRUE.equals(r.active()));
	}
}
