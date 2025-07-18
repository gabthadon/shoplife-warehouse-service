package com.softnet.shoplife.dto.request;

import com.softnet.shoplife.entity.WarehouseLocation;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WarehouseRequest {
    private String warehouseName;
    private Long maxCapacity;
    private String image;
    private String contactPhone;
    private String contactEmail;
    private WarehouseLocation warehouseLocation;
    private List<UnProProductRequest> unprocessedProducts;
    private List<ProProductRequest> processedProducts;

}
