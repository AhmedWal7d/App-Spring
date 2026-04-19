package com.ecommerce.ecommerce.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.catalog.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByCartOrderByIdAsc(Cart cart);

	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

	void deleteByCartAndProduct(Cart cart, Product product);
}
