package com.ecommerce.ecommerce.catalog;

import java.math.BigDecimal;

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

@Entity
@Table(name = "ECOMMERCE_PRODUCTS")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
	@SequenceGenerator(name = "prod_seq", sequenceName = "ECOM_PROD_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SKU", length = 64, unique = true)
	private String sku;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID", nullable = true)
	private Category category;

	@Column(name = "PRODUCT_HREF", length = 512)
	private String productHref;

	@Column(name = "IMAGE_URL", nullable = false, length = 1024)
	private String imageUrl;

	@Column(name = "RATING", nullable = false)
	private double rating = 5.0;

	@Column(name = "TITLE_AR", nullable = false, length = 512)
	private String titleAr;

	@Column(name = "TITLE_EN", nullable = false, length = 512)
	private String titleEn;

	@Column(name = "CURRENT_PRICE", nullable = false, precision = 14, scale = 2)
	private BigDecimal currentPrice;

	@Column(name = "ORIGINAL_PRICE", precision = 14, scale = 2)
	private BigDecimal originalPrice;

	@Column(name = "CURRENCY_AR", length = 32)
	private String currencyAr = "ج.م";

	@Column(name = "CURRENCY_EN", length = 32)
	private String currencyEn = "EGP";

	@Column(name = "DISCOUNT_AR", length = 64)
	private String discountPercentAr;

	@Column(name = "DISCOUNT_EN", length = 64)
	private String discountPercentEn;

	@Column(name = "INSTALLMENT_AR", length = 255)
	private String installmentSummaryAr;

	@Column(name = "INSTALLMENT_EN", length = 255)
	private String installmentSummaryEn;

	@Column(name = "PROMO_AR", length = 255)
	private String promoFooterAr;

	@Column(name = "PROMO_EN", length = 255)
	private String promoFooterEn;

	@Column(name = "CATEGORY_BADGE_AR", length = 120)
	private String categoryBadgeAr;

	@Column(name = "CATEGORY_BADGE_EN", length = 120)
	private String categoryBadgeEn;

	@Column(name = "SPECIAL_BADGE_AR", length = 120)
	private String specialBadgeAr;

	@Column(name = "SPECIAL_BADGE_EN", length = 120)
	private String specialBadgeEn;

	@Column(name = "FEATURED_ON_HOME", nullable = false)
	private boolean featuredOnHome;

	@Column(name = "HOME_SORT_ORDER", nullable = false)
	private int homeSortOrder;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getProductHref() {
		return productHref;
	}

	public void setProductHref(String productHref) {
		this.productHref = productHref;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
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

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getCurrencyAr() {
		return currencyAr;
	}

	public void setCurrencyAr(String currencyAr) {
		this.currencyAr = currencyAr;
	}

	public String getCurrencyEn() {
		return currencyEn;
	}

	public void setCurrencyEn(String currencyEn) {
		this.currencyEn = currencyEn;
	}

	public String getDiscountPercentAr() {
		return discountPercentAr;
	}

	public void setDiscountPercentAr(String discountPercentAr) {
		this.discountPercentAr = discountPercentAr;
	}

	public String getDiscountPercentEn() {
		return discountPercentEn;
	}

	public void setDiscountPercentEn(String discountPercentEn) {
		this.discountPercentEn = discountPercentEn;
	}

	public String getInstallmentSummaryAr() {
		return installmentSummaryAr;
	}

	public void setInstallmentSummaryAr(String installmentSummaryAr) {
		this.installmentSummaryAr = installmentSummaryAr;
	}

	public String getInstallmentSummaryEn() {
		return installmentSummaryEn;
	}

	public void setInstallmentSummaryEn(String installmentSummaryEn) {
		this.installmentSummaryEn = installmentSummaryEn;
	}

	public String getPromoFooterAr() {
		return promoFooterAr;
	}

	public void setPromoFooterAr(String promoFooterAr) {
		this.promoFooterAr = promoFooterAr;
	}

	public String getPromoFooterEn() {
		return promoFooterEn;
	}

	public void setPromoFooterEn(String promoFooterEn) {
		this.promoFooterEn = promoFooterEn;
	}

	public String getCategoryBadgeAr() {
		return categoryBadgeAr;
	}

	public void setCategoryBadgeAr(String categoryBadgeAr) {
		this.categoryBadgeAr = categoryBadgeAr;
	}

	public String getCategoryBadgeEn() {
		return categoryBadgeEn;
	}

	public void setCategoryBadgeEn(String categoryBadgeEn) {
		this.categoryBadgeEn = categoryBadgeEn;
	}

	public String getSpecialBadgeAr() {
		return specialBadgeAr;
	}

	public void setSpecialBadgeAr(String specialBadgeAr) {
		this.specialBadgeAr = specialBadgeAr;
	}

	public String getSpecialBadgeEn() {
		return specialBadgeEn;
	}

	public void setSpecialBadgeEn(String specialBadgeEn) {
		this.specialBadgeEn = specialBadgeEn;
	}

	public boolean isFeaturedOnHome() {
		return featuredOnHome;
	}

	public void setFeaturedOnHome(boolean featuredOnHome) {
		this.featuredOnHome = featuredOnHome;
	}

	public int getHomeSortOrder() {
		return homeSortOrder;
	}

	public void setHomeSortOrder(int homeSortOrder) {
		this.homeSortOrder = homeSortOrder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
