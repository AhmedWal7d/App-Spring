package com.ecommerce.ecommerce.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.cart.CartService;
import com.ecommerce.ecommerce.web.dto.AddCartItemRequest;
import com.ecommerce.ecommerce.web.dto.CartResponse;
import com.ecommerce.ecommerce.web.dto.UpdateCartItemRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/web/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebCartController {

	private final CartService cartService;

	public WebCartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping
	public CartResponse get(@AuthenticationPrincipal UserDetails principal) {
		return cartService.getCart(principal.getUsername());
	}

	@PostMapping("/items")
	public CartResponse add(@AuthenticationPrincipal UserDetails principal, @Valid @RequestBody AddCartItemRequest body) {
		return cartService.addItem(principal.getUsername(), body);
	}

	@PutMapping("/items/{productId}")
	public CartResponse updateQty(
			@AuthenticationPrincipal UserDetails principal,
			@PathVariable long productId,
			@Valid @RequestBody UpdateCartItemRequest body) {
		return cartService.updateQuantity(principal.getUsername(), productId, body);
	}

	@DeleteMapping("/items/{productId}")
	public CartResponse remove(@AuthenticationPrincipal UserDetails principal, @PathVariable long productId) {
		return cartService.removeItem(principal.getUsername(), productId);
	}
}
