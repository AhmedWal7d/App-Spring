package com.ecommerce.ecommerce.auth;

import java.util.Locale;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.web.Messages;

import com.ecommerce.ecommerce.auth.dto.AuthResponse;
import com.ecommerce.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.ecommerce.auth.dto.UserProfileResponse;
import com.ecommerce.ecommerce.security.JwtService;
import com.ecommerce.ecommerce.user.AppUser;
import com.ecommerce.ecommerce.user.AppUserRepository;

@Service
public class AuthService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(
			AppUserRepository appUserRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		String email = normalizeEmail(request.email());
		if (appUserRepository.existsByEmail(email)) {
			throw new EmailAlreadyRegisteredException();
		}
		AppUser user = new AppUser();
		user.setFirstName(request.firstName().trim());
		user.setLastName(request.lastName().trim());
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(trimPassword(request.password())));
		user.setPhone(request.phone().trim());
		user.setAuthority("ROLE_USER");
		try {
			appUserRepository.save(user);
			appUserRepository.flush();
		} catch (DataIntegrityViolationException ex) {
			throw new EmailAlreadyRegisteredException();
		}
		String token = jwtService.generateToken(user.getEmail());
		String fullName = (user.getFirstName() + " " + user.getLastName()).trim();
		return AuthResponse.of(201, Messages.REGISTER_OK, fullName, token, user.getEmail());
	}

	public AuthResponse login(LoginRequest request) {
		String email = normalizeEmail(request.email());
		AppUser user = appUserRepository.findByEmail(email)
				.orElseThrow(InvalidLoginException::new);
		String storedHash = user.getPasswordHash();
		if (storedHash == null || storedHash.isBlank()) {
			throw new InvalidLoginException();
		}
		String hash = storedHash.trim();
		String pwd = trimPassword(request.password());
		if (!passwordEncoder.matches(pwd, hash) && !passwordEncoder.matches(request.password(), hash)) {
			throw new InvalidLoginException();
		}
		String token = jwtService.generateToken(user.getEmail());
		String fullName = (user.getFirstName() + " " + user.getLastName()).trim();
		return AuthResponse.of(200, Messages.LOGIN_OK, fullName, token, user.getEmail());
	}

	public UserProfileResponse currentProfile(String email) {
		AppUser user = appUserRepository.findByEmail(normalizeEmail(email))
				.orElseThrow(() -> new BadCredentialsException("Invalid session"));
		String fullName = (user.getFirstName() + " " + user.getLastName()).trim();
		return UserProfileResponse.of(200, Messages.PROFILE_OK, user.getEmail(), fullName, user.getPhone());
	}

	private static String normalizeEmail(String email) {
		return email.trim().toLowerCase(Locale.ROOT);
	}

	private static String trimPassword(String password) {
		return password == null ? null : password.trim();
	}
}
