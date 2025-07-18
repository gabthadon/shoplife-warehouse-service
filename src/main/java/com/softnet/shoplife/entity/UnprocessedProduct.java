package com.softnet.shoplife.entity;


import com.softnet.shoplife.enums.ProductState;
import com.softnet.shoplife.enums.StockStatus;
import com.softnet.shoplife.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UnprocessedProduct extends BaseEntityWithoutTime{
    private String category;
    private String subCategory;
    private String productName;
    private BigDecimal purchasedPrice;
    private Long unitWeight;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private LocalDate storageLife;
    private String stockId;
    @Enumerated(EnumType.STRING)
    private StockStatus availabilityStatus;
    @Enumerated(EnumType.STRING)
    private ProductState productState;
    private String supplier;
    private LocalDate purchaseDate;
    @ManyToOne
    @JoinColumn(name = "warehouseInventory_id")
    private WarehouseInventory warehouseInventory;
}
