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

import com.ecommerce.ecommerce.admin.dto.HomeMainSlideUpsertRequest;
import com.ecommerce.ecommerce.home.entity.HomeMainSlide;
import com.ecommerce.ecommerce.home.repository.HomeMainSlideRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin/home/main-slides", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMainSlideController {

	private final HomeMainSlideRepository homeMainSlideRepository;

	public AdminMainSlideController(HomeMainSlideRepository homeMainSlideRepository) {
		this.homeMainSlideRepository = homeMainSlideRepository;
	}

	@GetMapping
	public List<HomeMainSlide> list() {
		return homeMainSlideRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public HomeMainSlide create(@Valid @RequestBody HomeMainSlideUpsertRequest body) {
		HomeMainSlide e = new HomeMainSlide();
		apply(e, body);
		return homeMainSlideRepository.save(e);
	}

	@PutMapping("/{id}")
	public HomeMainSlide update(@PathVariable long id, @Valid @RequestBody HomeMainSlideUpsertRequest body) {
		HomeMainSlide e = homeMainSlideRepository.findById(id).orElseThrow(ContentNotFoundException::new);
		apply(e, body);
		return homeMainSlideRepository.save(e);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		if (!homeMainSlideRepository.existsById(id)) {
			throw new ContentNotFoundException();
		}
		homeMainSlideRepository.deleteById(id);
	}

	private static void apply(HomeMainSlide e, HomeMainSlideUpsertRequest r) {
		e.setImageUrl(r.imageUrl().trim());
		e.setTitleAr(r.titleAr().trim());
		e.setTitleEn(r.titleEn().trim());
		e.setSubtitleAr(trimNull(r.subtitleAr()));
		e.setSubtitleEn(trimNull(r.subtitleEn()));
		e.setCtaLabelAr(trimNull(r.ctaLabelAr()));
		e.setCtaLabelEn(trimNull(r.ctaLabelEn()));
		e.setCtaHref(trimNull(r.ctaHref()));
		e.setSortOrder(r.sortOrder());
		e.setActive(Boolean.TRUE.equals(r.active()));
	}

	private static String trimNull(String s) {
		if (s == null) {
			return null;
		}
		String t = s.trim();
		return t.isEmpty() ? null : t;
	}
}
