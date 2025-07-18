package com.softnet.shoplife.services.impl;

import com.softnet.shoplife.dto.request.CartRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.CartResponse;
import com.softnet.shoplife.dto.responses.ProProductResponse;
import com.softnet.shoplife.entity.Cart;
import com.softnet.shoplife.entity.ProcessedProducts;
import com.softnet.shoplife.entity.ProductImages;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.repository.CartRepository;
import com.softnet.shoplife.repository.ProProductRepository;
import com.softnet.shoplife.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp  implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProProductRepository proProductRepository;

    public List<CartResponse> addToCart(CartRequest cartRequest, Long productId) {
        Optional<ProcessedProducts> processedProducts = proProductRepository.findById(productId);
        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity());
        cart.setCustomerId(cartRequest.getCustomerId());
        cart.setProcessedProductsId(processedProducts.get().getId());

        //Check if productexist in the database

        if (processedProducts.isEmpty()) {
            throw new InvalidRequestException("Product not found");
        } else {
            Cart cartResponseEntity = cartRepository.save(cart);

            CartResponse cartResponse = new CartResponse();
            cartResponse.setQuantity(cartResponseEntity.getQuantity());
            cartResponse.setCustomerId(cartResponseEntity.getCustomerId());
            cartResponse.setProcessedProductId(cartResponseEntity.getProcessedProductsId());
cartResponse.setProductName(processedProducts.get().getProductName());
cartResponse.setCategory(processedProducts.get().getCategory());
            List<ProductImages> productImages = processedProducts.get().getProductImages();
cartResponse.setImageUrl(productImages.get(0).getImageUrl());

            List<CartResponse> cartResponseList = new ArrayList<>();
            cartResponseList.add(cartResponse);
            return cartResponseList;
        }
    }


        @Override
    public List<CartResponse> getCartList(String customerId) {
        List<Cart> cartList = cartRepository.findByCustomerId(customerId);

        List<CartResponse> cartResponseList = new ArrayList<>();

        for (Cart cart : cartList) {
            CartResponse cartResponse = new CartResponse();
           Optional<ProcessedProducts> proProductResponse = proProductRepository.findById(cart.getProcessedProductsId());
            cartResponse.setQuantity(cart.getQuantity());
            cartResponse.setCustomerId(cart.getCustomerId());
            cartResponse.setProcessedProductId(proProductResponse.get().getId());
            cartResponse.setProductName(proProductResponse.get().getProductName());
            cartResponse.setCategory(proProductResponse.get().getCategory());
            List<ProductImages> productImages = proProductResponse.get().getProductImages();
            cartResponse.setImageUrl(productImages.get(0).getImageUrl());
            cartResponseList.add(cartResponse);
        }
        return cartResponseList;
    }

    @Override
    public ResponseEntity<String> deleteAnItemFromCart(Long cartId) {

        try {
            if (cartRepository.existsById(cartId)) {
                cartRepository.deleteById(cartId);
                return ResponseEntity.ok("Item deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Does Not Exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }





@Transactional
    public ResponseEntity<String> deleteAllItemsFromCart(String customerId) {
try{
    List<CartResponse> cartList = getCartList(customerId);

    if (!cartList.isEmpty()) {
        cartRepository.deleteByCustomerId(customerId);
        return ResponseEntity.ok("All items deleted from cart successfully");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart is Empty");
    }
}catch(Exception e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error"+ e.getMessage());
}



    }

    @Override
    public ResponseEntity<ApiResponse<CartResponse>> updateCart(CartRequest cartRequest, Long id) {
        try {
            Optional<Cart> cart = cartRepository.findById(id);
            if (cart.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Cart ID not present", null));
            } else {
                 cartRepository.updateCarById(cartRequest.getQuantity(), id);
                return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Cart updated successfully", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error: " + e.getMessage(), null));
        }
    }



}
