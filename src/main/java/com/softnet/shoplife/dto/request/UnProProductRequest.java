package com.softnet.shoplife.dto.request;


import com.softnet.shoplife.enums.ProductState;
import com.softnet.shoplife.enums.StockStatus;
import com.softnet.shoplife.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnProProductRequest {
    private Long id;
    private String category;
    private String subCategory;
    private String productName;
    private Long totalQuantity;
    private BigDecimal purchasedPrice;
    private Long unitWeight;
    private StorageType storageType;
    private LocalDate storageLife;
    private String stockId;
    private ProductState productState;
    private StockStatus availabilityStatus;
    private String supplier;
    private LocalDate purchaseDate;
}
