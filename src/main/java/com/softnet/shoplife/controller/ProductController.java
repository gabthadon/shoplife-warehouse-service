package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.request.ProProductRequest;
import com.softnet.shoplife.dto.request.ProductRequestWrapper;
import com.softnet.shoplife.dto.request.UnProProductRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.ProProductResponse;
import com.softnet.shoplife.dto.responses.UnProProductResponse;
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
        name = "Shop-Life - Warehouse-Inventory-Service Product Controller",
        description = "Exposes REST APIs for Product Controller"
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final WarehouseService warehouseService;

    @GetMapping("/get/all/unprocessed/products/by/warehouseId/{id}")
    @Operation(
            summary = "Get Unprocessed Products by Warehouse Id REST API",
            description = "This API Get Unprocessed Products by Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Unprocessed Products retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get Unprocessed Products by Warehouse Id")
            }
    )
    public ResponseEntity<ApiResponse<List<UnProProductResponse>>> getUnprocessedProductsByWarehouseId(@PathVariable Long id) {
        List<UnProProductResponse> response = warehouseService.getUnprocessedProductsByWarehouseId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @PostMapping("/add/unprocessed/products/to/warehouse/by/warehousesId")
    @Operation(
            summary = "Add Unprocessed Products to Warehouse by Warehouse Id REST API",
            description = "This API Adds Unprocessed Products to Warehouse by Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Adding List of Unprocessed Products to Warehouse successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to add Unprocessed Products to Warehouse by Warehouse Id")
            }
    )
    public ResponseEntity<ApiResponse<List<UnProProductResponse>>> addUnprocessedProductsToWarehouse(@RequestParam("id") Long id, @RequestBody ProductRequestWrapper products) {
        List<UnProProductResponse> response = warehouseService.addUnprocessedProductsToWarehouse(id, products);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @GetMapping("/get/unprocessed/product/by/{warehouseId}/{productId}")
    @Operation(
            summary = "Get Unprocessed Product in a Warehouse by Warehouse Id and Product Id REST API",
            description = "This API Gets Unprocessed Product in a Warehouse by Warehouse Id and Product Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Unprocessed Product gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id or Product Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get Unprocessed Products in a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<UnProProductResponse>> getUnprocessedProductByWarehouseIdAndProductId(@PathVariable Long warehouseId, @PathVariable Long productId) {
        UnProProductResponse response = warehouseService.getUnprocessedProductByWarehouseIdAndProductId(warehouseId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @PutMapping("/update/unprocessed/products/in/warehouse")
    @Operation(
            summary = "Update List of Unprocessed Products in a Warehouse  REST API",
            description = "This API Updates List of Unprocessed Products in a Warehouse by product Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Update List of Unprocessed Products in a Warehouse successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id or Product Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to update List of Unprocessed Products in a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<List<UnProProductResponse>>> updateListOfUnprocessedProductsInWarehouse(@RequestParam("id") Long id, @RequestBody List<UnProProductRequest> products) {
        List<UnProProductResponse> response = warehouseService.updateListOfUnprocessedProductsInWarehouse(id, products);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @DeleteMapping("/remove/unprocessed/products/from/warehouse/{id}")
    @Operation(
            summary = "Remove Unprocessed Products from a Warehouse by Id REST API",
            description = "This API Removes Unprocessed Products from a Warehouse by Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Removing List of Unprocessed Products from Warehouse successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid product Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to remove Unprocessed Products from Warehouse by Id")
            }
    )
    public ResponseEntity<Void> removeUnprocessedProductsFromWarehouse(@PathVariable Long id, @RequestBody List<Long> productIds) {
        warehouseService.removeUnprocessedProductsFromWarehouse(id, productIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all/processed/products/by/warehouseId/{id}")
    @Operation(
            summary = "Get List of Processed Products in a Warehouse by Warehouse Id REST API",
            description = "This API Gets List of Processed Products in a Warehouse by Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Processed Products gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get List of Processed Products in a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<List<ProProductResponse>>> getProcessedProductsByWarehouseId(@PathVariable Long id) {
        List<ProProductResponse> response = warehouseService.getProcessedProductsByWarehouseId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @PostMapping("/add/processed/products/to/warehouse/by/warehousesId")
    @Operation(
            summary = "Add List of Processed Products to a Warehouse by Warehouse Id REST API",
            description = "This API Adds List of Processed Products to a Warehouse by Warehouse Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Processed Products added successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to add List of Processed Products to a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<List<ProProductResponse>>> addProcessedProductsToWarehouse(@RequestParam("id") Long id, @RequestBody List<ProProductRequest> products) {
        List<ProProductResponse> response = warehouseService.addProcessedProductsToWarehouse(id, products);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @GetMapping("/get/processed/product/by/{warehouseId}/{productId}")
    @Operation(
            summary = "Get a Processed Product in a Warehouse by Warehouse Id and Product Id REST API",
            description = "This API Gets a Processed Product in a Warehouse by Warehouse Id and Product Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Processed Product gotten successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id or Product Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to get Processed Products in a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<ProProductResponse>> getProcessedProductByWarehouseIdAndProductId(@PathVariable Long warehouseId, @PathVariable Long productId) {
        ProProductResponse response = warehouseService.getProcessedProductByWarehouseIdAndProductId(warehouseId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @PutMapping("/update/processed/products/in/warehouse")
    @Operation(
            summary = "Update List of Processed Products in a Warehouse by Warehouse Id REST API",
            description = "This API Updates List of Processed Products in a Warehouse by Warehouse Id and product Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Processed Products updated successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid warehouse Id or Product Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to update List of Processed Products in a Warehouse")
            }
    )
    public ResponseEntity<ApiResponse<List<ProProductResponse>>> updateListOfProcessedProductsInWarehouse(@RequestParam("id") Long id, @RequestBody List<ProProductRequest> products) {
        List<ProProductResponse> response = warehouseService.updateListOfProcessedProductsInWarehouse(id, products);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseUtil.success(response));
    }

    @DeleteMapping("/remove/processed/products/from/warehouse/{id}")
    @Operation(
            summary = "Remove List of Processed Products from a Warehouse by Id REST API",
            description = "This API Removes List of Processed Products from a Warehouse by Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Processed Products removed successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request: Invalid Id"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to update List of Processed Products in a Warehouse")
            }
    )
    public ResponseEntity<Void> removeProcessedProductsFromWarehouse(@PathVariable Long id, @RequestBody List<Long> productIds) {
        warehouseService.removeProcessedProductsFromWarehouse(id, productIds);
        return ResponseEntity.ok().build();
    }


}
