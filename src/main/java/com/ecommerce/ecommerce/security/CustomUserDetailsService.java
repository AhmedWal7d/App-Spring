package com.ecommerce.ecommerce.security;

import java.util.Locale;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.user.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AppUserRepository appUserRepository;

	public CustomUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String email = username.trim().toLowerCase(Locale.ROOT);
		var user = appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		String role = user.getAuthority();
		if (role == null || role.isBlank()) {
			role = "ROLE_USER";
		}
		return User.withUsername(user.getEmail())
				.password(user.getPasswordHash())
				.authorities(role)
				.build();
	}
}
