package com.softnet.shoplife.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponse {
    private Long id;
    private String username;
    private String productName;
    private String productPrimaryImage;
    private String comment;
    private Long processedProductsId;


}
