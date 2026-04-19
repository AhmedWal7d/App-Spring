package com.ecommerce.ecommerce.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.home.entity.HomeMainSlide;

public interface HomeMainSlideRepository extends JpaRepository<HomeMainSlide, Long> {

	List<HomeMainSlide> findByActiveTrueOrderBySortOrderAsc();
}
