package com.softnet.shoplife.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String username;
    private String productName;
    private String productPrimaryImage;
    private String comment;
    private Long processedProductsId;

}
