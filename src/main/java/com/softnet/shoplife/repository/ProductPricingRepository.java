package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.ProductPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPricingRepository extends JpaRepository<ProductPricing, Long> {

    List<ProductPricing> findByProductName(String productName);
}
