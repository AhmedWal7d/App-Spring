package com.ecommerce.ecommerce.favorite;

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
import com.ecommerce.ecommerce.web.dto.FavoriteItemDto;

@Service
public class FavoritesService {

	private final AppUserRepository appUserRepository;
	private final ProductRepository productRepository;
	private final UserFavoriteRepository userFavoriteRepository;

	public FavoritesService(
			AppUserRepository appUserRepository,
			ProductRepository productRepository,
			UserFavoriteRepository userFavoriteRepository) {
		this.appUserRepository = appUserRepository;
		this.productRepository = productRepository;
		this.userFavoriteRepository = userFavoriteRepository;
	}

	@Transactional(readOnly = true)
	public List<FavoriteItemDto> list(String email) {
		AppUser user = loadUser(email);
		return userFavoriteRepository.findByUserOrderByIdAsc(user).stream()
				.map(uf -> toDto(uf.getId(), uf.getProduct()))
				.toList();
	}

	@Transactional
	public FavoriteItemDto add(String email, long productId) {
		AppUser user = loadUser(email);
		Product product = productRepository.findById(productId)
				.filter(Product::isActive)
				.orElseThrow(ProductNotFoundException::new);
		if (userFavoriteRepository.existsByUserAndProduct(user, product)) {
			return userFavoriteRepository.findByUserAndProduct(user, product)
					.map(uf -> toDto(uf.getId(), uf.getProduct()))
					.orElseThrow();
		}
		UserFavorite row = new UserFavorite();
		row.setUser(user);
		row.setProduct(product);
		try {
			UserFavorite saved = userFavoriteRepository.save(row);
			return toDto(saved.getId(), product);
		} catch (DataIntegrityViolationException ex) {
			return userFavoriteRepository.findByUserAndProduct(user, product)
					.map(uf -> toDto(uf.getId(), uf.getProduct()))
					.orElseThrow(() -> ex);
		}
	}

	@Transactional
	public void remove(String email, long productId) {
		AppUser user = loadUser(email);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		userFavoriteRepository.deleteByUserAndProduct(user, product);
	}

	private AppUser loadUser(String email) {
		return appUserRepository.findByEmail(email.trim().toLowerCase(Locale.ROOT))
				.orElseThrow(() -> new IllegalStateException("User not found"));
	}

	private static FavoriteItemDto toDto(long favoriteId, Product p) {
		Category cat = p.getCategory();
		return new FavoriteItemDto(
				favoriteId,
				p.getId(),
				cat != null ? cat.getId() : null,
				cat != null ? cat.getTitleAr() : null,
				cat != null ? cat.getTitleEn() : null,
				p.getSku(),
				p.getTitleAr(),
				p.getTitleEn(),
				p.getImageUrl(),
				p.getCurrentPrice(),
				p.getCurrencyAr(),
				p.getCurrencyEn(),
				p.getProductHref());
	}
}
