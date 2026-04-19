package com.ecommerce.ecommerce.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.home.entity.MidBanner;

public interface MidBannerRepository extends JpaRepository<MidBanner, Long> {

	List<MidBanner> findByActiveTrueOrderBySortOrderAsc();

	long countByActiveTrue();

	long countByActiveTrueAndIdNot(Long id);
}
