package com.softnet.shoplife.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductPricingRequest {
    private String productName;
    private String productSubCategory;
    private Double pricePerKilogram;
    private Integer salePercentage;
    private String addedBy;
}
