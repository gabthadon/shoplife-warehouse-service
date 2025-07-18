package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.responses.InFlowCountResponse;
import com.softnet.shoplife.dto.responses.InFlowResponse;

public interface InFlowOutFlowService {

    /**
     * Method to get the total sum of all InFlowUnitQty.
     *
     * @return the total sum of InFlowUnitQty.
     */
    InFlowCountResponse getTotalSumOfInFlowUnitQty();

    /**
     * Method to get the total sum of InFlowUnitQty that matches a specific unit value.
     *
     * @param unitValue the specific unit value to match.
     * @return the total sum of InFlowUnitQty that matches the unit value.
     */
    InFlowCountResponse getTotalSumOfByInFlowUnitQty(Long unitValue);

    /**
     * Method to get the total sum of InFlowUnitQty for a specific warehouse inventory.
     *
     * @param warehouseInventoryId the ID of the warehouse inventory.
     * @return the total sum of InFlowUnitQty for the specified warehouse inventory.
     */
    InFlowCountResponse getInFlowTotalSumByWarehouseInventory(Long warehouseInventoryId);

    /**
     * Method to get the total sum of InFlowUnitQty for a specific warehouse inventory and unit value.
     *
     * @param warehouseInventoryId the ID of the warehouse inventory.
     * @param unitValue            the specific unit value to match.
     * @return the total sum of InFlowUnitQty for the specified warehouse inventory and unit value.
     */
    InFlowResponse getTotalSumByWarehouseInventoryAndInFlowUnitQty(Long warehouseInventoryId, Long unitValue);
}
