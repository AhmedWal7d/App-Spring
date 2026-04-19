package com.ecommerce.ecommerce.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ECOMMERCE_USERS")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ecommerce_user_seq")
	@SequenceGenerator(name = "ecommerce_user_seq", sequenceName = "ECOMMERCE_USER_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false, length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, length = 100)
	private String lastName;

	@Column(name = "EMAIL", nullable = false, unique = true, length = 255)
	private String email;

	@Column(name = "PASSWORD_HASH", nullable = false, length = 255)
	private String passwordHash;

	@Column(name = "PHONE", nullable = true, length = 30)
	private String phone;

	/** Spring Security authority, e.g. {@code ROLE_USER} or {@code ROLE_ADMIN}. */
	@Column(name = "USER_AUTHORITY", nullable = true, length = 32)
	private String authority;

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@PostLoad
	void trimOraclePadding() {
		if (email != null) {
			email = email.trim();
		}
		if (passwordHash != null) {
			passwordHash = passwordHash.trim();
		}
		if (firstName != null) {
			firstName = firstName.trim();
		}
		if (lastName != null) {
			lastName = lastName.trim();
		}
		if (phone != null) {
			phone = phone.trim();
		}
		if (authority != null) {
			authority = authority.trim();
		}
	}
}

