package com.ecommerce.ecommerce.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.auth.dto.ForgotPasswordRequest;
import com.ecommerce.ecommerce.auth.dto.ForgotPasswordResponse;
import com.ecommerce.ecommerce.auth.dto.ResetPasswordRequest;
import com.ecommerce.ecommerce.auth.dto.ResetPasswordResponse;
import com.ecommerce.ecommerce.auth.reset.PasswordOtp;
import com.ecommerce.ecommerce.auth.reset.PasswordOtpRepository;
import com.ecommerce.ecommerce.user.AppUserRepository;
import com.ecommerce.ecommerce.web.Messages;

@Service
public class PasswordResetService {

	private final AppUserRepository appUserRepository;
	private final PasswordOtpRepository otpRepository;
	private final PasswordEncoder passwordEncoder;
	private final OtpEmailService otpEmailService;
	private final String jwtSecret;
	private final long otpTtlMinutes;

	public PasswordResetService(
			AppUserRepository appUserRepository,
			PasswordOtpRepository otpRepository,
			PasswordEncoder passwordEncoder,
			OtpEmailService otpEmailService,
			@Value("${app.jwt.secret}") String jwtSecret,
			@Value("${app.otp.ttl-minutes:10}") long otpTtlMinutes) {
		this.appUserRepository = appUserRepository;
		this.otpRepository = otpRepository;
		this.passwordEncoder = passwordEncoder;
		this.otpEmailService = otpEmailService;
		this.jwtSecret = jwtSecret;
		this.otpTtlMinutes = otpTtlMinutes;
	}

	@Transactional
	public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
		String email = normalizeEmail(request.email());
		var userOpt = appUserRepository.findByEmail(email);
		if (userOpt.isEmpty()) {
			return ForgotPasswordResponse.of(HttpStatus.OK.value(), Messages.FORGOT_GENERIC, null, null);
		}
		otpRepository.deleteActiveByEmail(email);
		otpRepository.flush();
		String otp = newSixDigitOtp();
		String otpHash = hashOtp(email, otp);
		Instant expiresAt = Instant.now().plus(otpTtlMinutes, ChronoUnit.MINUTES);
		PasswordOtp row = new PasswordOtp();
		row.setEmail(email);
		row.setOtpHash(otpHash);
		row.setExpiresAt(expiresAt);
		otpRepository.save(row);
		OtpSendResult send = otpEmailService.sendPasswordResetOtp(email, otp, (int) otpTtlMinutes);
		if (!send.success()) {
			otpRepository.delete(row);
			return switch (send.failure()) {
				case NO_MAIL_SENDER -> ForgotPasswordResponse.of(
						HttpStatus.SERVICE_UNAVAILABLE.value(), Messages.MAIL_NO_SENDER, "no_mail_sender", null);
				case MISSING_CREDENTIALS -> ForgotPasswordResponse.of(
						HttpStatus.SERVICE_UNAVAILABLE.value(), Messages.MAIL_NO_CREDS, "missing_credentials", null);
				case SMTP_REJECTED -> ForgotPasswordResponse.of(
						HttpStatus.BAD_GATEWAY.value(), Messages.MAIL_SMTP_FAIL, "smtp_rejected", send.smtpHint());
			};
		}
		return ForgotPasswordResponse.of(
				HttpStatus.OK.value(),
				Messages.forgotCodeSent(email),
				null,
				null);
	}

	@Transactional
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
		String email = normalizeEmail(request.email());
		String otp = OtpNormalizer.toSixDigitAscii(request.otp());
		if (otp.length() != 6) {
			throw new InvalidResetTokenException();
		}
		String otpHash = hashOtp(email, otp);
		PasswordOtp row = otpRepository
				.findActiveForReset(email, otpHash, Instant.now())
				.orElseThrow(InvalidResetTokenException::new);
		var user = appUserRepository.findByEmail(email).orElseThrow(InvalidResetTokenException::new);
		user.setPasswordHash(passwordEncoder.encode(trimPassword(request.newPassword())));
		appUserRepository.save(user);
		row.setConsumedAt(Instant.now());
		otpRepository.save(row);
		return ResetPasswordResponse.of(HttpStatus.OK.value(), Messages.RESET_OK);
	}

	private String hashOtp(String email, String otp) {
		return sha256Hex(otp + "|" + email + "|" + jwtSecret);
	}

	private static String newSixDigitOtp() {
		int n = 100_000 + new SecureRandom().nextInt(900_000);
		return String.format("%06d", n);
	}

	private static String sha256Hex(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			return HexFormat.of().formatHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private static String normalizeEmail(String email) {
		return email.trim().toLowerCase(Locale.ROOT);
	}

	private static String trimPassword(String password) {
		return password == null ? null : password.trim();
	}
}
