package com.ecommerce.ecommerce.home;

public class MaxActiveMidBannersException extends RuntimeException {
	public MaxActiveMidBannersException() {
		super("At most 2 active mid-page banners are allowed");
	}
}
