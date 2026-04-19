package com.ecommerce.ecommerce.favorite;

import java.time.Instant;

import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.user.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name = "ECOMMERCE_USER_FAVORITES",
		uniqueConstraints = @UniqueConstraint(name = "UK_FAV_USER_PRODUCT", columnNames = { "USER_ID", "PRODUCT_ID" }))
public class UserFavorite {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fav_seq")
	@SequenceGenerator(name = "fav_seq", sequenceName = "ECOM_FAV_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private AppUser user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	@Column(name = "CREATED_AT", nullable = false)
	private Instant createdAt = Instant.now();

	public Long getId() {
		return id;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
