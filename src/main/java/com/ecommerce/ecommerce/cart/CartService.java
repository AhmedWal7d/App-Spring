package com.ecommerce.ecommerce.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalog.Category;
import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.catalog.ProductNotFoundException;
import com.ecommerce.ecommerce.catalog.ProductRepository;
import com.ecommerce.ecommerce.user.AppUser;
import com.ecommerce.ecommerce.user.AppUserRepository;
import com.ecommerce.ecommerce.web.dto.AddCartItemRequest;
import com.ecommerce.ecommerce.web.dto.CartLineResponse;
import com.ecommerce.ecommerce.web.dto.CartResponse;
import com.ecommerce.ecommerce.web.dto.UpdateCartItemRequest;

@Service
public class CartService {

	private final AppUserRepository appUserRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public CartService(
			AppUserRepository appUserRepository,
			ProductRepository productRepository,
			CartRepository cartRepository,
			CartItemRepository cartItemRepository) {
		this.appUserRepository = appUserRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}

	@Transactional(readOnly = true)
	public CartResponse getCart(String email) {
		AppUser user = loadUser(email);
		return cartRepository.findByUser(user).map(this::toResponse)
				.orElse(new CartResponse(0, List.of(), BigDecimal.ZERO));
	}

	@Transactional
	public CartResponse addItem(String email, AddCartItemRequest request) {
		AppUser user = loadUser(email);
		Cart cart = cartRepository.findByUser(user).orElseGet(() -> newCart(user));
		Product product = productRepository.findById(request.productId())
				.filter(Product::isActive)
				.orElseThrow(ProductNotFoundException::new);
		CartItem line = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);
		if (line == null) {
			line = new CartItem();
			line.setCart(cart);
			line.setProduct(product);
			line.setQuantity(request.quantity());
			cartItemRepository.save(line);
		}
		else {
			line.setQuantity(line.getQuantity() + request.quantity());
			cartItemRepository.save(line);
		}
		return toResponse(cartRepository.findById(cart.getId()).orElseThrow());
	}

	@Transactional
	public CartResponse updateQuantity(String email, long productId, UpdateCartItemRequest request) {
		AppUser user = loadUser(email);
		Cart cart = cartRepository.findByUser(user).orElseThrow(CartNotFoundException::new);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		CartItem line = cartItemRepository.findByCartAndProduct(cart, product).orElseThrow(CartItemNotFoundException::new);
		line.setQuantity(request.quantity());
		cartItemRepository.save(line);
		return toResponse(cart);
	}

	@Transactional
	public CartResponse removeItem(String email, long productId) {
		AppUser user = loadUser(email);
		Cart cart = cartRepository.findByUser(user).orElseThrow(CartNotFoundException::new);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		cartItemRepository.deleteByCartAndProduct(cart, product);
		return cartRepository.findByUser(user).map(this::toResponse)
				.orElse(new CartResponse(0, List.of(), BigDecimal.ZERO));
	}

	private AppUser loadUser(String email) {
		return appUserRepository.findByEmail(email.trim().toLowerCase(Locale.ROOT))
				.orElseThrow(() -> new IllegalStateException("User not found"));
	}

	private Cart newCart(AppUser user) {
		Cart c = new Cart();
		c.setUser(user);
		try {
			return cartRepository.save(c);
		} catch (DataIntegrityViolationException ex) {
			return cartRepository.findByUser(user).orElseThrow(() -> ex);
		}
	}

	private CartResponse toResponse(Cart cart) {
		List<CartItem> items = cartItemRepository.findByCartOrderByIdAsc(cart);
		List<CartLineResponse> lines = new ArrayList<>();
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem it : items) {
			Product p = it.getProduct();
			Category cat = p.getCategory();
			BigDecimal unit = p.getCurrentPrice();
			BigDecimal lineTotal = unit.multiply(BigDecimal.valueOf(it.getQuantity()));
			total = total.add(lineTotal);
			lines.add(new CartLineResponse(
					it.getId(),
					p.getId(),
					cat != null ? cat.getId() : null,
					cat != null ? cat.getTitleAr() : null,
					cat != null ? cat.getTitleEn() : null,
					p.getSku(),
					p.getTitleAr(),
					p.getTitleEn(),
					p.getImageUrl(),
					unit,
					p.getCurrencyAr(),
					p.getCurrencyEn(),
					it.getQuantity(),
					lineTotal));
		}
		return new CartResponse(cart.getId(), lines, total);
	}
}
