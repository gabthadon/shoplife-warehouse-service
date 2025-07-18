package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.request.CartRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.CartResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    List<CartResponse> addToCart(CartRequest cartRequest, Long productId);

   List<CartResponse> getCartList(String customerId);

   ResponseEntity<String> deleteAnItemFromCart(Long cartId);

    ResponseEntity<String> deleteAllItemsFromCart(String customerId);

    ResponseEntity<ApiResponse<CartResponse>> updateCart(CartRequest cartRequest, Long id);
}
