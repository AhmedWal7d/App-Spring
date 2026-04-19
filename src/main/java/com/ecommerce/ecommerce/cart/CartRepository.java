package com.ecommerce.ecommerce.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.user.AppUser;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUser(AppUser user);
}
