package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductSubCategory extends BaseEntityWithTime {
    private String name;
    @OneToMany(mappedBy = "productSubCategory", cascade = CascadeType.ALL)
    private List<ProductType> productTypes;

    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;


}
