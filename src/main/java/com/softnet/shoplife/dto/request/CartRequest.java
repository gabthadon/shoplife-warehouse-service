package com.softnet.shoplife.dto.request;

import com.softnet.shoplife.entity.ProcessedProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private String customerId;
    private Long processedProductId;
    private Long quantity;

}
