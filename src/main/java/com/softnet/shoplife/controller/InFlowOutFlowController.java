package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.InFlowCountResponse;
import com.softnet.shoplife.dto.responses.InFlowResponse;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.services.InFlowOutFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Shop-Life - Warehouse-Inventory-Service InFlowOutFlow Controller",
        description = "Exposes REST APIs for InFlowOutFlow Controller"
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class InFlowOutFlowController {
    private final InFlowOutFlowService inFlowOutFlowService;

    @GetMapping("/get/inflow/unit/total/count")
    @Operation(
            summary = "Get a Total Count Of All InFLow Units",
            description = "This API is used to Get a Total Count Of All InFLow Unit",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total Count Gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to Get Total Count")
            }
    )
    public ResponseEntity<ApiResponse<InFlowCountResponse>> getTotalCountOfInFlowUnit() {
        try {
            InFlowCountResponse response = inFlowOutFlowService.getTotalSumOfInFlowUnitQty();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Total Count Gotten successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Warehouse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Total Count", null));
        }
    }

    @GetMapping("/get/inflow/unit/total/count/by/unit/value/{unit-value}")
    @Operation(
            summary = "Get a Total Count Of All InFLow Units By Unit Value REST API",
            description = "This API is used to Get a Total Count Of All InFLow Unit By a specific Unit Value",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total Count Gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to Get Total Count")
            }
    )
    public ResponseEntity<ApiResponse<InFlowCountResponse>> getTotalCountOfInFlowUnitByUnitValue(@PathVariable("unit-value") Long unitValue) {
        try {
            InFlowCountResponse response = inFlowOutFlowService.getTotalSumOfByInFlowUnitQty(unitValue);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Total Count Gotten successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Warehouse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Total Count", null));
        }
    }

    @GetMapping("/get/inflow/unit/total/count/by/warehouseId/{warehouse_id}")
    @Operation(
            summary = "Get a Total Count Of All InFLow Units By WarehouseId REST API",
            description = "This API is used to Get a Total Count Of All InFLow Unit By a specific WarehouseId",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total Count Gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to Get Total Count")
            }
    )
    public ResponseEntity<ApiResponse<InFlowCountResponse>> getTotalCountOfInFlowUnitByWarehouseId(@PathVariable("warehouse_id") Long warehouseId) {
        try {
            InFlowCountResponse response = inFlowOutFlowService.getInFlowTotalSumByWarehouseInventory(warehouseId);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Total Count Gotten successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Warehouse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Total Count", null));
        }
    }

    @GetMapping("/get/inflow/unit/total/count/by/warehouseId/unitValue")
    @Operation(
            summary = "Get a Total Count Of All InFLow Units By WarehouseId and Unit-Value REST API",
            description = "This API is used to Get a Total Count Of All InFLow Unit By a specific WarehouseId and Unit-Value",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total Count Gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to Get Total Count")
            }
    )
    public ResponseEntity<ApiResponse<InFlowResponse>> getTotalCountOfInFlowUnitByWarehouseIdAndInFlowUnitValue(@RequestParam("warehouse_id") Long warehouseId, @RequestParam("unit_value") Long unitValue) {
        try {
            InFlowResponse response = inFlowOutFlowService.getTotalSumByWarehouseInventoryAndInFlowUnitQty(warehouseId, unitValue);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Total Count Gotten successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Warehouse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Get Total Count", null));
        }
    }



}
