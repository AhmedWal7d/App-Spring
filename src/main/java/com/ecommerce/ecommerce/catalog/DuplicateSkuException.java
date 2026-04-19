package com.ecommerce.ecommerce.catalog;

/** Another product already uses this SKU (unique constraint on {@link Product#getSku()}). */
public class DuplicateSkuException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
