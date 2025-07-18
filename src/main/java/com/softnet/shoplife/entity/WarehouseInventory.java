package com.softnet.shoplife.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WarehouseInventory extends BaseEntityWithTime{
    @NotBlank
    private String warehouseName;

    @NotNull
    @Embedded
    private WarehouseLocation warehouseLocation;

    @OneToMany(mappedBy = "warehouseInventory", cascade = CascadeType.ALL)
    private List<UnprocessedProduct> unprocessedProducts;

    @OneToMany(mappedBy = "warehouseInventory", cascade = CascadeType.ALL)
    private List<ProcessedProducts> processedProducts;

    @OneToMany(mappedBy = "warehouseInventory", cascade = CascadeType.ALL)
    private List<InFlowOutFlow> inFlowOutFlow;

    @NotNull
    private Long maxCapacity;

    private String image;

    @NotBlank
    private String contactPhone;

    @NotBlank
    @Email
    private String contactEmail;
}
