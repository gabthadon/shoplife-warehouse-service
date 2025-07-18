package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InFlowOutFlow extends BaseEntityWithTime{
    private Long inFlowUnitQty;
    private Long outFlowUnitQty;
    @ManyToOne
    @JoinColumn(name = "warehouseInventory_id")
    private WarehouseInventory warehouseInventory;

}
