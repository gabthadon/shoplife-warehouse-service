package com.softnet.shoplife.dto.responses;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SubCategoryAndProductTypeResponse {
    private ProductSubCategoryResponse subCategory;
    private List<ProductTypeResponse> productTypes;
}
