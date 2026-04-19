package com.ecommerce.ecommerce.home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ECOMMERCE_MID_BANNERS")
public class MidBanner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mid_ban_seq")
	@SequenceGenerator(name = "mid_ban_seq", sequenceName = "ECOM_MID_BAN_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "IMAGE_URL", nullable = false, length = 1024)
	private String imageUrl;

	@Column(name = "TITLE_AR", length = 255)
	private String titleAr;

	@Column(name = "TITLE_EN", length = 255)
	private String titleEn;

	@Column(name = "LINK_HREF", length = 512)
	private String linkHref;

	@Column(name = "SORT_ORDER", nullable = false)
	private int sortOrder;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
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
