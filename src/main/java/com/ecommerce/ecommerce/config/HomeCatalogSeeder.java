package com.ecommerce.ecommerce.config;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.ecommerce.ecommerce.catalog.Category;
import com.ecommerce.ecommerce.catalog.CategoryRepository;
import com.ecommerce.ecommerce.catalog.Product;
import com.ecommerce.ecommerce.catalog.ProductRepository;
import com.ecommerce.ecommerce.home.entity.HomeCategoryTile;
import com.ecommerce.ecommerce.home.entity.HomeMainSlide;
import com.ecommerce.ecommerce.home.entity.MidBanner;
import com.ecommerce.ecommerce.home.repository.HomeCategoryTileRepository;
import com.ecommerce.ecommerce.home.repository.HomeMainSlideRepository;
import com.ecommerce.ecommerce.home.repository.MidBannerRepository;

@Configuration
public class HomeCatalogSeeder {

	private static final Logger log = LoggerFactory.getLogger(HomeCatalogSeeder.class);

	@Bean
	@Order(20)
	ApplicationRunner seedHomeCatalogIfEmpty(
			HomeMainSlideRepository slides,
			HomeCategoryTileRepository tiles,
			MidBannerRepository mids,
			CategoryRepository categories,
			ProductRepository products) {
		return args -> {
			if (products.count() > 0) {
				return;
			}
			log.info("Seeding demo home catalog (DB was empty)");

			Category catElectronics = ensureCategory(categories, "electronics", "إلكترونيات", "Electronics", 1);
			Category catKitchen = ensureCategory(categories, "kitchen", "أجهزة المطبخ", "Kitchen appliances", 2);
			Category catTv = ensureCategory(categories, "tvs", "تلفزيونات", "TVs", 3);
			Category catSmall = ensureCategory(categories, "small-appliances", "أجهزة صغيرة", "Small appliances", 4);
			Category catMobile = ensureCategory(categories, "mobile", "موبايل وتابلت", "Mobile & tablets", 5);

			slides.save(slide(1, "https://picsum.photos/seed/hero1/1600/700",
					"تقسيط بدون فوائد", "Interest-free installments",
					"اشترِ الآن وادفع على أقساط مريحة.", "Buy now and pay in comfortable installments.",
					"اشتري الآن", "Shop now", "/offers/installments"));
			slides.save(slide(2, "https://picsum.photos/seed/hero2/1600/700",
					"عروض الصيف", "Summer offers",
					"خصومات على الأجهزة المنزلية والمطبخ.", "Discounts on home and kitchen appliances.",
					"تسوق العروض", "Shop offers", "/offers/summer"));
			slides.save(slide(3, "https://picsum.photos/seed/hero3/1600/700",
					"إلكترونيات", "Electronics",
					"أحدث الأجهزة والشاشات مع ضمان معتمد.", "Latest devices and screens with warranty.",
					"اكتشف المزيد", "Discover more", "/category/electronics"));
			slides.save(slide(4, "https://picsum.photos/seed/hero4/1600/700",
					"مطبخك أذكى", "Smarter kitchen",
					"أدوات مطبخ حديثة توفر الوقت والجهد.", "Modern kitchen tools that save time.",
					"عرض المنتجات", "View products", "/category/kitchen"));
			slides.save(slide(5, "https://picsum.photos/seed/hero5/1600/700",
					"شحن سريع", "Fast delivery",
					"توصيل لباب المنزل خلال أيام عمل قليلة.", "Delivery to your door in a few working days.",
					"ابدأ الطلب", "Start order", "/shipping"));

			tiles.save(tile(1, "https://picsum.photos/seed/cat-ps/400/400", "إلكترونيات", "Electronics"));
			tiles.save(tile(2, "https://picsum.photos/seed/cat-pc/400/400", "كمبيوتر", "Computers"));
			tiles.save(tile(3, "https://picsum.photos/seed/cat-kitchen/400/400", "أجهزة المطبخ", "Kitchen appliances"));
			tiles.save(tile(4, "https://picsum.photos/seed/cat-small/400/400", "أجهزة صغيرة", "Small appliances"));
			tiles.save(tile(5, "https://picsum.photos/seed/cat-large/400/400", "أجهزة كبيرة", "Large appliances"));
			tiles.save(tile(6, "https://picsum.photos/seed/cat-tv/400/400", "تلفزيونات", "TVs"));
			tiles.save(tile(7, "https://picsum.photos/seed/cat-phone/400/400", "موبايل وتابلت", "Mobile & tablets"));
			tiles.save(tile(8, "https://picsum.photos/seed/cat-offer/400/400", "عروض الصيف", "Summer offers"));
			tiles.save(tile(9, "https://picsum.photos/seed/cat-beauty/400/400", "صحة وجمال", "Health & beauty"));

			mids.save(mid(0, "https://picsum.photos/seed/mid1/900/320", "عرض اليوم", "Deal of the day", "/promos/today"));
			mids.save(mid(1, "https://picsum.photos/seed/mid2/900/320", "تقسيط 0%", "0% installments", "/installments"));

			products.save(p(
					catMobile,
					"SKU-ITEL-T11",
					"/products/itel-t11-earbuds",
					"https://picsum.photos/seed/chosen-earbuds/600/600",
					5.0,
					"سماعة أذن آيتل T11 إيربودز — بطارية حتى 40 ساعة، أسود",
					"itel T11 True Wireless Earbuds — up to 40h battery, black",
					new BigDecimal("777"),
					new BigDecimal("1036"),
					"خصم 25٪",
					"-25%",
					"من 65 ج.م/شهر من خلال تقسيط",
					"From 65 EGP/month with installment plans",
					"0% مقدم، 0% فوائد",
					"0% down payment, 0% interest",
					"منتجات راية",
					"Raya products",
					null,
					null,
					1));
			products.save(p(
					catKitchen,
					"SKU-TORNADO-MW",
					"/products/tornado-microwave-25l",
					"https://picsum.photos/seed/chosen-mw/600/600",
					4.8,
					"ميكروويف تورنادو سولو 25 لتر — 900 وات، فضي",
					"Tornado Solo microwave 25L — 900W, silver",
					new BigDecimal("3299"),
					new BigDecimal("3899"),
					"خصم 15٪",
					"-15%",
					"من 275 ج.م/شهر من خلال تقسيط",
					"From 275 EGP/month with installment plans",
					"0% مقدم، 0% فوائد",
					"0% down payment, 0% interest",
					"منتجات راية",
					"Raya products",
					null,
					null,
					2));
			products.save(p(
					catTv,
					"SKU-SAM-TV43",
					"/products/samsung-tv-43-uhd",
					"https://picsum.photos/seed/chosen-tv/600/600",
					4.9,
					"تلفزيون سامسونج سمارت 43 بوصة LED — 4K UHD",
					"Samsung 43\" Smart LED TV — 4K UHD",
					new BigDecimal("12499"),
					new BigDecimal("13999"),
					"خصم 11٪",
					"-11%",
					"من 1041 ج.م/شهر من خلال تقسيط",
					"From 1,041 EGP/month with installment plans",
					"0% مقدم، 0% فوائد",
					"0% down payment, 0% interest",
					"منتجات راية",
					"Raya products",
					null,
					null,
					3));
			products.save(p(
					catSmall,
					"SKU-TORQ-VAC",
					"/products/torq-vacuum-2000w",
					"https://picsum.photos/seed/chosen-vac/600/600",
					4.7,
					"مكنسة كهربائية تورك 2000 وات — أحمر وأسود",
					"Torq vacuum cleaner 2000W — red and black",
					new BigDecimal("1899"),
					new BigDecimal("2299"),
					"خصم 17٪",
					"-17%",
					"من 158 ج.م/شهر من خلال تقسيط",
					"From 158 EGP/month with installment plans",
					"0% مقدم، 0% فوائد",
					"0% down payment, 0% interest",
					"منتجات راية",
					"Raya products",
					null,
					null,
					4));
			products.save(p(
					catElectronics,
					"SKU-KEN-IRON",
					"/products/kenwood-steam-iron-2000w",
					"https://picsum.photos/seed/chosen-iron/600/600",
					5.0,
					"مكواة بخار كينوود 2000 وات — بنفسجي",
					"Kenwood steam iron 2000W — purple",
					new BigDecimal("899"),
					new BigDecimal("1199"),
					"خصم 25٪",
					"-25%",
					"من 75 ج.م/شهر من خلال تقسيط",
					"From 75 EGP/month with installment plans",
					"0% مقدم، 0% فوائد",
					"0% down payment, 0% interest",
					"منتجات راية",
					"Raya products",
					"الأكثر مبيعاً",
					"Best seller",
					5));
		};
	}

	private static Category ensureCategory(CategoryRepository repo, String slug, String ar, String en, int sort) {
		return repo.findBySlug(slug).orElseGet(() -> {
			Category c = new Category();
			c.setSlug(slug);
			c.setTitleAr(ar);
			c.setTitleEn(en);
			c.setSortOrder(sort);
			c.setActive(true);
			return repo.save(c);
		});
	}

	private static HomeMainSlide slide(
			int order,
			String img,
			String tAr,
			String tEn,
			String sAr,
			String sEn,
			String cAr,
			String cEn,
			String href) {
		HomeMainSlide e = new HomeMainSlide();
		e.setImageUrl(img);
		e.setTitleAr(tAr);
		e.setTitleEn(tEn);
		e.setSubtitleAr(sAr);
		e.setSubtitleEn(sEn);
		e.setCtaLabelAr(cAr);
		e.setCtaLabelEn(cEn);
		e.setCtaHref(href);
		e.setSortOrder(order);
		e.setActive(true);
		return e;
	}

	private static HomeCategoryTile tile(int order, String img, String ar, String en) {
		HomeCategoryTile e = new HomeCategoryTile();
		e.setImageUrl(img);
		e.setTitleAr(ar);
		e.setTitleEn(en);
		e.setSortOrder(order);
		e.setActive(true);
		return e;
	}

	private static MidBanner mid(int order, String img, String ar, String en, String href) {
		MidBanner b = new MidBanner();
		b.setImageUrl(img);
		b.setTitleAr(ar);
		b.setTitleEn(en);
		b.setLinkHref(href);
		b.setSortOrder(order);
		b.setActive(true);
		return b;
	}

	private static Product p(
			Category category,
			String sku,
			String href,
			String image,
			double rating,
			String titleAr,
			String titleEn,
			BigDecimal current,
			BigDecimal original,
			String dAr,
			String dEn,
			String iAr,
			String iEn,
			String pAr,
			String pEn,
			String cAr,
			String cEn,
			String sAr,
			String sEn,
			int homeOrder) {
		Product x = new Product();
		x.setCategory(category);
		x.setSku(sku);
		x.setProductHref(href);
		x.setImageUrl(image);
		x.setRating(rating);
		x.setTitleAr(titleAr);
		x.setTitleEn(titleEn);
		x.setCurrentPrice(current);
		x.setOriginalPrice(original);
		x.setDiscountPercentAr(dAr);
		x.setDiscountPercentEn(dEn);
		x.setInstallmentSummaryAr(iAr);
		x.setInstallmentSummaryEn(iEn);
		x.setPromoFooterAr(pAr);
		x.setPromoFooterEn(pEn);
		x.setCategoryBadgeAr(cAr);
		x.setCategoryBadgeEn(cEn);
		x.setSpecialBadgeAr(sAr);
		x.setSpecialBadgeEn(sEn);
		x.setFeaturedOnHome(true);
		x.setHomeSortOrder(homeOrder);
		x.setActive(true);
		return x;
	}
}
