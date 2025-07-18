package com.softnet.shoplife.repository;

import com.softnet.shoplife.dto.request.CartRequest;
import com.softnet.shoplife.dto.responses.CartResponse;
import com.softnet.shoplife.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomerId(String customerId);


    void deleteByCustomerId(String customerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart set quantity=:quantity WHERE id=:id", nativeQuery = true)
    void updateCarById(Long quantity, Long id);
}

