package com.softnet.shoplife.dto.responses;

import com.softnet.shoplife.enums.ProductState;
import com.softnet.shoplife.enums.StockStatus;
import com.softnet.shoplife.enums.StorageType;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UnProProductResponse {
    private Long id;
    private String category;
    private String subCategory;
    private String productName;
    private BigDecimal purchasedPrice;
    private Long unitWeight;
    private StorageType storageType;
    private String storageLife;
    private String stockId;
    private StockStatus availabilityStatus;
    private ProductState productState;
    private String supplier;
    private String purchaseDate;
}
