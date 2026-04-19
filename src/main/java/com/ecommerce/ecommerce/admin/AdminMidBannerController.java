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

import com.ecommerce.ecommerce.admin.dto.MidBannerUpsertRequest;
import com.ecommerce.ecommerce.home.entity.MidBanner;
import com.ecommerce.ecommerce.home.repository.MidBannerRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin/mid-banners", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMidBannerController {

	private final MidBannerRepository midBannerRepository;
	private final MidBannerAdminService midBannerAdminService;

	public AdminMidBannerController(MidBannerRepository midBannerRepository, MidBannerAdminService midBannerAdminService) {
		this.midBannerRepository = midBannerRepository;
		this.midBannerAdminService = midBannerAdminService;
	}

	@GetMapping
	public List<MidBanner> list() {
		return midBannerRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MidBanner create(@Valid @RequestBody MidBannerUpsertRequest body) {
		return midBannerAdminService.create(body);
	}

	@PutMapping("/{id}")
	public MidBanner update(@PathVariable long id, @Valid @RequestBody MidBannerUpsertRequest body) {
		return midBannerAdminService.update(id, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long id) {
		midBannerAdminService.delete(id);
	}
}
