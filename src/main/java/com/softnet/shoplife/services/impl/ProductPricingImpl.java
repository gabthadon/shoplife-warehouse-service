package com.softnet.shoplife.services.impl;

import com.softnet.shoplife.dto.request.ProductPricingRequest;
import com.softnet.shoplife.dto.responses.ProductPricingResponse;
import com.softnet.shoplife.entity.ProductPricing;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.repository.ProductPricingRepository;
import com.softnet.shoplife.services.ProductPricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPricingImpl implements ProductPricingService {
    private final ProductPricingRepository productPricingRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductPricingResponse>  createProductPricing(List<ProductPricingRequest> productPricingRequest) {
        List<ProductPricingResponse> createdProductPricing = new ArrayList<>();
        for (ProductPricingRequest request : productPricingRequest) {
            ProductPricing productPricing = modelMapper.map(request, ProductPricing.class);
            ProductPricing savedProductPricing = productPricingRepository.save(productPricing);
            createdProductPricing.add(modelMapper.map(savedProductPricing, ProductPricingResponse.class));
        }
        log.info("Successfully Created productPricing: {}", createdProductPricing);
        return createdProductPricing;
    }

    @Override
    public List<ProductPricingResponse> getAllProductPricing() {
        List<ProductPricing> allProductPricing = productPricingRepository.findAll();
        return allProductPricing.stream()
                .map(product -> modelMapper.map(product, ProductPricingResponse.class))
                .collect(Collectors.toList());    }

    @Override
    public List<ProductPricingResponse> getProductPricingByProductName(String productName) {
        List<ProductPricing> productPricingList = productPricingRepository.findByProductName(productName);
        return productPricingList.stream()
                .map(product -> modelMapper.map(product, ProductPricingResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductPricingResponse getProductPricingById(Long id) {
        ProductPricing productPricing = productPricingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProductPricing not found with id: " + id));
        log.info("Retrieved ProductPricing: {}", productPricing);
        return modelMapper.map(productPricing, ProductPricingResponse.class);
    }

    @Override
    public void deleteProductPricingById(List<Long> ids) {
        for (Long id : ids) {
            if (productPricingRepository.existsById(id)) {
                productPricingRepository.deleteById(id);
                log.info("ProductPricing with id {} deleted successfully", id);
            } else {
                throw new NotFoundException("ProductPricing not found with id: " + id);
            }
        }
    }
}
