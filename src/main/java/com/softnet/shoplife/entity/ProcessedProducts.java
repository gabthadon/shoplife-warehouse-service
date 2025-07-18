package com.softnet.shoplife.entity;

import com.softnet.shoplife.enums.StockStatus;
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
public class ProcessedProducts extends BaseEntityWithTime{
    private String category;
    private String subCategory;
    private String productName;
    private String description;
    private BigDecimal sellingPrice;
    private Long unitWeight;
    private String stockId;
    @Enumerated(EnumType.STRING)
    private StockStatus availabilityStatus;
    private String addedBy;
    private LocalDate addedDate;

    @OneToMany(mappedBy = "processedProducts", cascade = CascadeType.ALL)
    private List<ProductImages> productImages;

    @ManyToOne
    @JoinColumn(name = "warehouseInventory_id")
    private WarehouseInventory warehouseInventory;

    @OneToMany(mappedBy = "processedProducts", cascade = CascadeType.ALL)
    private List<ProductReviews> productReviews;
}

