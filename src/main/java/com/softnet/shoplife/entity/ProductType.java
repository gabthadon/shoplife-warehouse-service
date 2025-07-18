package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductType extends BaseEntityWithTime {
    private String name;
    @ManyToOne
    @JoinColumn(name = "productSubCategory_id")
    private ProductSubCategory productSubCategory;

}
