package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.request.ProductPricingRequest;
import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.dto.responses.ProductPricingResponse;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.services.ProductPricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Shop-Life - Warehouse-Inventory-Service ProductPricing Controller",
        description = "Exposes REST APIs for ProductPricing Controller"
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductPricingController {
    private final ProductPricingService productPricingService;

    @PostMapping("/create/new/productPricing")
    @Operation(
            summary = "Create a List of new Product Pricing REST API",
            description = "This API creates a List(Optional) of new Product Pricing",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product Pricing created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to create Product Pricing")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductPricingResponse>>> createNewProductPricing(@RequestBody List<ProductPricingRequest> productPricingRequest) {
        try {
            List<ProductPricingResponse> response = productPricingService.createProductPricing(productPricingRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "ProductPricing created successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating ProductPricing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create ProductPricing", null));
        }
    }

    @GetMapping("/get/all/productPricing")
    @Operation(
            summary = "Get a list of all Product Pricing REST API",
            description = "This API returns a list of all Product Pricing",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Product Pricing retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve list of Product Pricing")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductPricingResponse>>> getAllProductPricing() {
        try {
            List<ProductPricingResponse> response = productPricingService.getAllProductPricing();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of ProductPricing retrieved successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error retrieving list of ProductPricing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve list of ProductPricing", null));
        }
    }

    @GetMapping("/get/all/ProductPricing/by/productName/{productName}")
    @Operation(
            summary = "Get a list of all Product Pricing by productName REST API",
            description = "This API returns a list of all Product Pricing by productName",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Product Pricing retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve list of Product Pricing")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductPricingResponse>>> getAllProductPricingByProductName(@PathVariable String productName) {
        try {
            List<ProductPricingResponse> response = productPricingService.getProductPricingByProductName(productName);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of ProductPricing by productName: " + productName + " retrieved successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error retrieving list of ProductPricing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve list of ProductPricing by productName: " + productName + " ", null));
        }
    }

    @GetMapping("/get/productPricing/by/id/{id}")
    @Operation(
            summary = "Get a Product Pricing by Id REST API",
            description = "This API returns a Product Pricing by Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product Pricing retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve Product Pricing")
            }
    )
    public ResponseEntity<ApiResponse<ProductPricingResponse>> getProductPricingById(@PathVariable Long id) {
        try {
            ProductPricingResponse response = productPricingService.getProductPricingById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "ProductPricing retrieved successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error retrieving ProductPricing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve ProductPricing", null));
        }
    }

    @DeleteMapping("/delete/ProductPricing/by/ids")
    @Operation(
            summary = "Delete a list of Product Pricing by Ids REST API",
            description = "This API Deletes a list of all Product Pricing by their Id",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product Pricing Deleted successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to Delete Product Pricing")
            }
    )
    public ResponseEntity<ApiResponse<String>> deleteProductPricingById(@RequestBody List<Long> id) {
        try {
            productPricingService.deleteProductPricingById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product Pricing Deleted successfully", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error deleting Product Pricing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Delete Product Pricing", null));
        }
    }
}
