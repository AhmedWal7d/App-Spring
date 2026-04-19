package com.ecommerce.ecommerce.home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ECOMMERCE_HOME_MAIN_SLIDES")
public class HomeMainSlide {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_main_seq")
	@SequenceGenerator(name = "home_main_seq", sequenceName = "ECOM_HOME_MAIN_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "IMAGE_URL", nullable = false, length = 1024)
	private String imageUrl;

	@Column(name = "TITLE_AR", nullable = false, length = 255)
	private String titleAr;

	@Column(name = "TITLE_EN", nullable = false, length = 255)
	private String titleEn;

	@Column(name = "SUBTITLE_AR", length = 512)
	private String subtitleAr;

	@Column(name = "SUBTITLE_EN", length = 512)
	private String subtitleEn;

	@Column(name = "CTA_LABEL_AR", length = 120)
	private String ctaLabelAr;

	@Column(name = "CTA_LABEL_EN", length = 120)
	private String ctaLabelEn;

	@Column(name = "CTA_HREF", length = 512)
	private String ctaHref;

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

	public String getSubtitleAr() {
		return subtitleAr;
	}

	public void setSubtitleAr(String subtitleAr) {
		this.subtitleAr = subtitleAr;
	}

	public String getSubtitleEn() {
		return subtitleEn;
	}

	public void setSubtitleEn(String subtitleEn) {
		this.subtitleEn = subtitleEn;
	}

	public String getCtaLabelAr() {
		return ctaLabelAr;
	}

	public void setCtaLabelAr(String ctaLabelAr) {
		this.ctaLabelAr = ctaLabelAr;
	}

	public String getCtaLabelEn() {
		return ctaLabelEn;
	}

	public void setCtaLabelEn(String ctaLabelEn) {
		this.ctaLabelEn = ctaLabelEn;
	}

	public String getCtaHref() {
		return ctaHref;
	}

	public void setCtaHref(String ctaHref) {
		this.ctaHref = ctaHref;
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
