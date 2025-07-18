package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.request.ProductPricingRequest;
import com.softnet.shoplife.dto.responses.ProductPricingResponse;

import java.util.List;

public interface ProductPricingService {

    List<ProductPricingResponse> createProductPricing(List<ProductPricingRequest> productPricingRequest);
    List<ProductPricingResponse> getAllProductPricing();

    List<ProductPricingResponse> getProductPricingByProductName(String productName);
    ProductPricingResponse getProductPricingById(Long id);
    void deleteProductPricingById(List<Long> ids);
}
