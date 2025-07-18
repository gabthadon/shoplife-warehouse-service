package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImages extends BaseEntityWithTime{
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "processedProducts_id")
    private ProcessedProducts processedProducts;
}
