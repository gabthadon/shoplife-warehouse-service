package com.softnet.shoplife.dto.responses;

import com.softnet.shoplife.entity.ProcessedProducts;
import com.softnet.shoplife.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long quantity;
    private String customerId;
    private Long processedProductId;
    private String productName;
    private String category;
    private String imageUrl;



}
