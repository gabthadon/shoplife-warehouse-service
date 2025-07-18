package com.softnet.shoplife.services.impl;

import com.softnet.shoplife.dto.responses.InFlowCountResponse;
import com.softnet.shoplife.dto.responses.InFlowResponse;
import com.softnet.shoplife.entity.InFlowOutFlow;
import com.softnet.shoplife.repository.InFlowOutFlowRepository;
import com.softnet.shoplife.services.InFlowOutFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InFlowOutFlowServiceImpl implements InFlowOutFlowService {
    private final InFlowOutFlowRepository inFlowOutFlowRepository;
    @Override
    public InFlowCountResponse getTotalSumOfInFlowUnitQty() {
        Long totalSum = inFlowOutFlowRepository.sumAllInFlowUnitQty();
        return new InFlowCountResponse(totalSum != null ? totalSum : 0L);
    }

    @Override
    public InFlowCountResponse getTotalSumOfByInFlowUnitQty(Long unitValue) {
        Long totalSum = inFlowOutFlowRepository.sumByInFlowUnitQty(unitValue);
        return new InFlowCountResponse(totalSum != null ? totalSum : 0L);
    }

    @Override
    public InFlowCountResponse getInFlowTotalSumByWarehouseInventory(Long warehouseInventoryId) {
        Long totalSum = inFlowOutFlowRepository.sumByWarehouseInventoryId(warehouseInventoryId);
        return new InFlowCountResponse(totalSum != null ? totalSum : 0L);
    }

    @Override
    public InFlowResponse getTotalSumByWarehouseInventoryAndInFlowUnitQty(Long warehouseInventoryId, Long unitValue) {
        Long totalSum = inFlowOutFlowRepository.sumByWarehouseInventoryIdAndInFlowUnitQty(warehouseInventoryId, unitValue);
        InFlowOutFlow inFlowOutFlow = inFlowOutFlowRepository.findFirstByWarehouseInventoryIdAndInFlowUnitQtyOrderByCreatedDateDesc(warehouseInventoryId, unitValue);

        if (totalSum == null || inFlowOutFlow == null) {
            return new InFlowResponse(0L, null, warehouseInventoryId);
        } else {
            return new InFlowResponse(totalSum, String.valueOf(LocalDateTime.parse(inFlowOutFlow.getCreatedDate().toString())), warehouseInventoryId);
        }
    }
}
