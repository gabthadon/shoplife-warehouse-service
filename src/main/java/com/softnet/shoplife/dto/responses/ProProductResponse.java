package com.softnet.shoplife.dto.responses;

import com.softnet.shoplife.enums.StockStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProProductResponse {
    private Long id;
    private String category;
    private String subCategory;
    private String productName;
    private String description;
//    private String image;
    private BigDecimal sellingPrice;
    private Long unitWeight;
    private String stockId;
    private StockStatus availabilityStatus;
    private String addedBy;
    private String addedDate;
    private List<ProductImageResponse> productImages;
}
