package com.softnet.shoplife.dto.responses;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CategoryAndSubCategoryResponse {
    private ProductCategoryResponse category;
    private List<ProductSubCategoryResponse> subCategories;

}
