package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.UnprocessedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnProProductRepository extends JpaRepository<UnprocessedProduct, Long> {
    UnprocessedProduct findByProductName(String productName);
    UnprocessedProduct findByCategoryAndSubCategoryAndProductNameAndUnitWeight(String category, String subCategory, String productName, Long unitWeight);

    List<UnprocessedProduct> findAllByCategoryAndSubCategoryAndProductNameAndUnitWeight(String category, String subCategory, String productName, Long unitWeight);
    // Add custom query methods if needed
}