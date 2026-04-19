package com.ecommerce.ecommerce.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	@Query("select u from AppUser u where lower(trim(u.email)) = :email")
	Optional<AppUser> findByEmail(@Param("email") String email);

	@Query("select count(u) from AppUser u where lower(trim(u.email)) = :email")
	long countByEmailEquals(@Param("email") String email);

	default boolean existsByEmail(String email) {
		return countByEmailEquals(email) > 0;
	}
}
