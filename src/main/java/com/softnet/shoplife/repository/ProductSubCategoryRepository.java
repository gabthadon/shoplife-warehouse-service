package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.ProductCategory;
import com.softnet.shoplife.entity.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Long> {
    Optional<ProductSubCategory> findByNameIgnoreCase(String subCategoryName);

    List<ProductSubCategory> findByProductCategory(ProductCategory category);
}
