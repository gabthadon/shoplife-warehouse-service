package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductCategory extends BaseEntityWithTime {
    private String name;
    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<ProductSubCategory> subCategories;

}
