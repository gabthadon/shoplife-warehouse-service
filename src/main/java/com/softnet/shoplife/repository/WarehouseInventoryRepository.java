package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, Long> {
    boolean existsByWarehouseName(String warehouseName);
    // Add custom query methods if needed

    @Query(value = "SELECT SUM(LENGTH(wi.warehouse_location.country) + " +
            "LENGTH(wi.warehouse_location.state) + " +
            "LENGTH(wi.warehouse_location.lga) + " +
            "LENGTH(wi.warehouse_location.city_or_town) + " +
            "LENGTH(wi.warehouse_location.contact_address)) " +
            "FROM warehouse_inventory wi",
            nativeQuery = true)
    BigDecimal sumOfWarehouseLocation();

    @Query(value = "SELECT SUM(LENGTH(wi.state)) AS sum_of_warehouse_location FROM warehouse_inventory wi", nativeQuery = true)
    Long getSumOfWarehouseStateLocation();

    @Query(value = "select sum(max_capacity) from warehouse_inventory", nativeQuery = true)
    Long getTotalInventoryUnit();
}