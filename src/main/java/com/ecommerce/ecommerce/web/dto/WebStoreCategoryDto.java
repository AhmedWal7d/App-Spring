package com.ecommerce.ecommerce.web.dto;

/** تصنيف متجر (قائمة التصفية) — عربي + إنجليزي. */
public record WebStoreCategoryDto(long id, String slug, String titleAr, String titleEn) {
}
