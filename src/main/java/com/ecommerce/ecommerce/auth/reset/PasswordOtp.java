package com.ecommerce.ecommerce.auth.reset;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ECOMMERCE_OTP")
public class PasswordOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_seq")
	@SequenceGenerator(name = "otp_seq", sequenceName = "ECOMMERCE_OTP_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "EMAIL", nullable = false, length = 255)
	private String email;

	@Column(name = "OTP_HASH", nullable = false, length = 64)
	private String otpHash;

	@Column(name = "EXPIRES_AT", nullable = false)
	private Instant expiresAt;

	@Column(name = "CONSUMED_AT")
	private Instant consumedAt;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtpHash() {
		return otpHash;
	}

	public void setOtpHash(String otpHash) {
		this.otpHash = otpHash;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Instant getConsumedAt() {
		return consumedAt;
	}

	public void setConsumedAt(Instant consumedAt) {
		this.consumedAt = consumedAt;
	}
}
