package com.softnet.shoplife.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductPricingResponse {
    private String productName;
    private String productSubCategory;
    private Double pricePerKilogram;
    private Integer salePercentage;
    private String addedBy;
}
