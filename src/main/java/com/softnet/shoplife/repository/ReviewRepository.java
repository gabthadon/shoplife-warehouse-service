package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.ProductReviews;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ProductReviews, Long> {
   List <ProductReviews> findByUsername(String username);

    ProductReviews findProductReviewsById(Long id);

}
