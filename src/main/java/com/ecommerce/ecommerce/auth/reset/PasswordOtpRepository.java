package com.ecommerce.ecommerce.auth.reset;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordOtpRepository extends JpaRepository<PasswordOtp, Long> {

	@Query(
			"select o from PasswordOtp o where lower(trim(o.email)) = :email and o.otpHash = :hash "
					+ "and o.consumedAt is null and o.expiresAt > :now")
	Optional<PasswordOtp> findActiveForReset(@Param("email") String email, @Param("hash") String hash, @Param("now") Instant now);

	@Modifying
	@Query("delete from PasswordOtp o where lower(trim(o.email)) = :email and o.consumedAt is null")
	void deleteActiveByEmail(@Param("email") String email);
}
