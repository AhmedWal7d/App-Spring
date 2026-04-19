package com.ecommerce.ecommerce.favorite;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.user.AppUser;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

	List<UserFavorite> findByUserOrderByIdAsc(AppUser user);

	Optional<UserFavorite> findByUserAndProduct(AppUser user, Product product);

	void deleteByUserAndProduct(AppUser user, Product product);

	boolean existsByUserAndProduct(AppUser user, Product product);
}
