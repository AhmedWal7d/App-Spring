package com.ecommerce.ecommerce.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.ecommerce.user.AppUser;
import com.ecommerce.ecommerce.user.AppUserRepository;

@Configuration
public class AdminBootstrap {

	private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

	@Bean
	@Order(1)
	ApplicationRunner createDefaultAdmin(
			AppUserRepository users,
			PasswordEncoder passwordEncoder,
			@Value("${app.admin.email:}") String adminEmail,
			@Value("${app.admin.password:}") String adminPassword) {
		return args -> {
			String email = adminEmail == null ? "" : adminEmail.trim().toLowerCase(Locale.ROOT);
			if (email.isEmpty() || adminPassword == null || adminPassword.isBlank()) {
				log.info("Skipping admin bootstrap: set app.admin.email and app.admin.password");
				return;
			}
			if (users.existsByEmail(email)) {
				return;
			}
			AppUser admin = new AppUser();
			admin.setFirstName("Admin");
			admin.setLastName("User");
			admin.setEmail(email);
			admin.setPasswordHash(passwordEncoder.encode(adminPassword.trim()));
			admin.setPhone("000");
			admin.setAuthority("ROLE_ADMIN");
			try {
				users.save(admin);
				log.info("Created ROLE_ADMIN user {}", email);
			} catch (DataIntegrityViolationException ex) {
				log.info("ROLE_ADMIN for {} was not created (already exists or constraint). Login with existing account.", email);
			}
		};
	}
}
