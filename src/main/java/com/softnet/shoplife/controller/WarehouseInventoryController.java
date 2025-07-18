package com.softnet.shoplife.controller;


import com.softnet.shoplife.dto.request.WarehouseRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.WarehouseResponse;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.services.WarehouseService;
import com.softnet.shoplife.utils.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Shop-Life - Warehouse-Inventory-Service Warehouse Controller",
        description = "Exposes REST APIs for Warehouse Controller"
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class WarehouseInventoryController {
    private final WarehouseService warehouseService;

    @PostMapping("/create/warehouse")
    @Operation(
            summary = "Create a new warehouse and add products to it",
            description = "Create a new warehouse and also add products to it (Optional)",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Warehouse created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to create Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<WarehouseResponse>> createWarehouseInventory(@RequestBody WarehouseRequest warehouseRequest) {
        try {
            WarehouseResponse response = warehouseService.createWarehouse(warehouseRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Warehouse created successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating Warehouse", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create Warehouse", null));
        }
    }

    @GetMapping("/get/all/warehouses")
    @Operation(
            summary = "Get a list of all warehouses REST API",
            description = "This API returns a list of all warehouses",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of warehouses retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve list of warehouses")
            }
    )
    public ResponseEntity<ApiResponse<List<WarehouseResponse>>> getAllWarehouses() {
        List<WarehouseResponse> response = warehouseService.getAllWarehouses();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @GetMapping("/get/warehouse/{id}")
    @Operation(
            summary = "Get Warehouse by ID REST API",
            description = "This API returns a warehouse by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Warehouse retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get warehouse by ID")
            }
    )
    public ResponseEntity<ApiResponse<WarehouseResponse>> getWarehouseById(@PathVariable Long id) {
        WarehouseResponse response = warehouseService.getWarehouseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @PutMapping("/update/warehouse/{id}")
    @Operation(
            summary = "Update Warehouse by ID REST API",
            description = "This API Updates Warehouse Details by the Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to update warehouse by Id")
            }
    )
    public ResponseEntity<ApiResponse<WarehouseResponse>> updateWarehouseInventory(@PathVariable Long id, @RequestBody WarehouseRequest warehouseRequest) {
        WarehouseResponse response = warehouseService.updateWarehouse(id, warehouseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @DeleteMapping("/delete/warehouse/{id}")
    @Operation(
            summary = "Delete Warehouse by ID REST API",
            description = "This API deletes Warehouse From Database by the Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Warehouse deleted successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to delete warehouse by Id")
            }
    )
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/total/state")
    @Operation(
            summary = "Get Total count of Warehouse locations REST API",
            description = "This API Get total count of all Warehouse locations",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total count of Warehouse locations retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request:"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get total count of Warehouse locations")
            }
    )
    public ResponseEntity<ApiResponse<Long>> getWarehouseTotalLocation() {
        Long response = warehouseService.totalWarehouseLocation();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @GetMapping("/get/total/inventory-unit")
    @Operation(
            summary = "Get Total count of all Warehouse Unit REST API",
            description = "This API Get Total count of all Warehouse Unit or Capacity",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Total count of all Warehouse Unit gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request:"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get total count of all Warehouse Unit")
            }
    )
    public ResponseEntity<ApiResponse<Long>> getTotalWarehouseUnit() {
        Long response = warehouseService.totalInventoryUnit();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

}
