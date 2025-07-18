package com.softnet.shoplife.dto.request;

import com.softnet.shoplife.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProProductRequest {
    private Long id;
    private String category;
    private String subCategory;
    private String productName;
    private String description;
    private Long totalQuantity;
    private BigDecimal sellingPrice;
    private Long unitWeight;
    private String stockId;
    private StockStatus availabilityStatus;
    private String addedBy;
    private List<ProductImageRequest> productImages;
}
