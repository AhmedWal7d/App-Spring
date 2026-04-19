package com.ecommerce.ecommerce.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p left join fetch p.category where p.featuredOnHome = true and p.active = true order by p.homeSortOrder asc")
	List<Product> findByFeaturedOnHomeTrueAndActiveTrueOrderByHomeSortOrderAsc();

	@Query("select distinct p from Product p left join fetch p.category where p.active = true order by p.id asc")
	List<Product> findAllActiveWithCategory();

	@Query("select distinct p from Product p join fetch p.category where p.category.id = :categoryId and p.active = true order by p.id asc")
	List<Product> findByCategoryIdActiveWithCategory(@Param("categoryId") long categoryId);

	boolean existsBySku(String sku);

	boolean existsBySkuAndIdNot(String sku, long id);

	long countByCategoryId(long categoryId);
}
