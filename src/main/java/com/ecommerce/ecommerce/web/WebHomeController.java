package com.ecommerce.ecommerce.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.home.HomeReadService;
import com.ecommerce.ecommerce.web.dto.WebHomeResponse;

@RestController
@RequestMapping(value = "/api/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebHomeController {

	private final HomeReadService homeReadService;

	public WebHomeController(HomeReadService homeReadService) {
		this.homeReadService = homeReadService;
	}

	/** تجميع الصفحة الرئيسية: سلايدر أساسي، تصنيفات، اخترنا لك، إعلانين في المنتصف. */
	@GetMapping("/home")
	public WebHomeResponse home() {
		return homeReadService.loadWebHome();
	}
}
