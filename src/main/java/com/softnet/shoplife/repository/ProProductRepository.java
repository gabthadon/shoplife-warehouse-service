package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.ProcessedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProProductRepository extends JpaRepository<ProcessedProducts, Long> {

}
