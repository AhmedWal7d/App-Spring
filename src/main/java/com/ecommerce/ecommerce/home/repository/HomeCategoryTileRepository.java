package com.ecommerce.ecommerce.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.home.entity.HomeCategoryTile;

public interface HomeCategoryTileRepository extends JpaRepository<HomeCategoryTile, Long> {

	List<HomeCategoryTile> findByActiveTrueOrderBySortOrderAsc();
}
