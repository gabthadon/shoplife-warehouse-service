package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.request.CartRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.CartResponse;
import com.softnet.shoplife.entity.Cart;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.services.CartService;
import com.softnet.shoplife.utils.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequestMapping("/cart")
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<ApiResponse<List<CartResponse>>> addToCart(@RequestBody CartRequest cartRequest, @PathVariable("productId") Long productId){

        try {
            List<CartResponse> cartResponses  =   cartService.addToCart(cartRequest, productId);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(cartResponses));
        }catch (InvalidRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Adding To Cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed To Add T0 Cart", null));
        }

    }

    //Get List Of All Items On The Customer's Cart
    @GetMapping("/get/cart/{customerId}")
    public ResponseEntity<ApiResponse<List<CartResponse>>> getCartList(@PathVariable("customerId") String customerId){
        List<CartResponse> cartResponse = cartService.getCartList(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(cartResponse));
    }


    //Delete One Item From Cart
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteAnItemFromCart(@PathVariable("cartId") Long cartId){
        return cartService.deleteAnItemFromCart(cartId);
    }

    @DeleteMapping("/delete/all/{customerId}")
    public ResponseEntity<String> deleteAllItemsFromCart(@PathVariable("customerId") String customerId){
        return cartService.deleteAllItemsFromCart(customerId);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<ApiResponse<CartResponse>> updateCart(@RequestBody CartRequest cartRequest, @PathVariable("id") Long id){
        return cartService.updateCart(cartRequest, id);
    }
}
