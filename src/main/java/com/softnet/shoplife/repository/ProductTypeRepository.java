package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.ProductSubCategory;
import com.softnet.shoplife.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    Optional<ProductType> findByNameIgnoreCase(String typeName);

    List<ProductType> findByProductSubCategory(ProductSubCategory subCategory);
}
