package com.softnet.shoplife.entity;

import lombok.*;

import javax.persistence.Entity;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductPricing extends BaseEntityWithTime{
    private String productName;
    private String productSubCategory;
    private Double pricePerKilogram;
    private Integer salePercentage;
    private String addedBy;
}
