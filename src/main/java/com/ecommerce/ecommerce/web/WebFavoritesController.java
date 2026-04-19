package com.ecommerce.ecommerce.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.favorite.FavoritesService;
import com.ecommerce.ecommerce.web.dto.FavoriteItemDto;

/**
 * User wishlist / favorites. Optional query {@code ?lang=en} or {@code ?lang=ar} matches the rest of the API for
 * clients that append language to every URL (error messages from shared handlers respect {@code lang}).
 */
@RestController
@RequestMapping(value = "/api/web/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebFavoritesController {

	private final FavoritesService favoritesService;

	public WebFavoritesController(FavoritesService favoritesService) {
		this.favoritesService = favoritesService;
	}

	@GetMapping
	public List<FavoriteItemDto> list(@AuthenticationPrincipal UserDetails principal) {
		return favoritesService.list(principal.getUsername());
	}

	@PostMapping("/{productId}")
	@ResponseStatus(HttpStatus.CREATED)
	public FavoriteItemDto add(@AuthenticationPrincipal UserDetails principal, @PathVariable long productId) {
		return favoritesService.add(principal.getUsername(), productId);
	}

	@DeleteMapping("/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@AuthenticationPrincipal UserDetails principal, @PathVariable long productId) {
		favoritesService.remove(principal.getUsername(), productId);
	}
}
