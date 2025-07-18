package com.softnet.shoplife.dto.responses;

import com.softnet.shoplife.entity.WarehouseLocation;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class WarehouseResponse {
    private Long id;
    private String warehouseName;
    private Long maxCapacity;
    private String image;
    private String contactPhone;
    private String contactEmail;
    private WarehouseLocation warehouseLocation;
    private List<UnProProductResponse> unprocessedProducts;
    private List<ProProductResponse> processedProducts;

}
