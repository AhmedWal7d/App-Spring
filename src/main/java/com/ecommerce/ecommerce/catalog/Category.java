package com.ecommerce.ecommerce.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ECOMMERCE_CATEGORIES")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_seq")
	@SequenceGenerator(name = "cat_seq", sequenceName = "ECOM_CAT_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SLUG", nullable = false, unique = true, length = 128)
	private String slug;

	@Column(name = "TITLE_AR", nullable = false, length = 255)
	private String titleAr;

	@Column(name = "TITLE_EN", nullable = false, length = 255)
	private String titleEn;

	@Column(name = "SORT_ORDER", nullable = false)
	private int sortOrder;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitleAr() {
		return titleAr;
	}

	public void setTitleAr(String titleAr) {
		this.titleAr = titleAr;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
