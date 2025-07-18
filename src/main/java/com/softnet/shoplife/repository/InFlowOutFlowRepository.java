package com.softnet.shoplife.repository;

import com.softnet.shoplife.entity.InFlowOutFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InFlowOutFlowRepository extends JpaRepository<InFlowOutFlow, Long> {
    @Query("SELECT SUM(i.inFlowUnitQty) FROM InFlowOutFlow i")
    Long sumAllInFlowUnitQty();

    @Query("SELECT SUM(i.inFlowUnitQty) FROM InFlowOutFlow i WHERE i.inFlowUnitQty = :unitValue")
    Long sumByInFlowUnitQty(@Param("unitValue") Long unitValue);

    @Query("SELECT SUM(i.inFlowUnitQty) FROM InFlowOutFlow i WHERE i.warehouseInventory.id = :warehouseInventoryId")
    Long sumByWarehouseInventoryId(@Param("warehouseInventoryId") Long warehouseInventoryId);

    @Query("SELECT SUM(i.inFlowUnitQty) FROM InFlowOutFlow i WHERE i.warehouseInventory.id = :warehouseInventoryId AND i.inFlowUnitQty = :unitValue")
    Long sumByWarehouseInventoryIdAndInFlowUnitQty(@Param("warehouseInventoryId") Long warehouseInventoryId, @Param("unitValue") Long unitValue);

    InFlowOutFlow findFirstByWarehouseInventoryIdAndInFlowUnitQtyOrderByCreatedDateDesc(Long warehouseInventoryId, Long unitValue);

}
