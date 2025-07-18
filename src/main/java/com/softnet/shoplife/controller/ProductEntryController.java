package com.softnet.shoplife.controller;

import com.softnet.shoplife.dto.request.ProductCategoryRequest;
import com.softnet.shoplife.dto.request.ProductPricingRequest;
import com.softnet.shoplife.dto.request.ProductSubCategoryRequest;
import com.softnet.shoplife.dto.request.ProductTypeRequest;
import com.softnet.shoplife.dto.responses.*;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.services.ProductEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Shop-Life - Warehouse-Inventory-Service ProductEntry Controller",
        description = "Exposes REST APIs for ProductEntry Controller"
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductEntryController {
    private final ProductEntryService productEntryService;

    @PostMapping("/create/new/product/category")
    @Operation(
            summary = "Create a List of new Product Category REST API",
            description = "This API creates a List(Optional) of new Product Category",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product Category created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to create Product Category")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductCategoryResponse>>> createNewProductCategory(@RequestBody List<ProductCategoryRequest> productCategoryRequest) {
        try {
            List<ProductCategoryResponse> response = productEntryService.createProductCategory(productCategoryRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "ProductCategory created successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating ProductCategory", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create ProductCategory", null));
        }
    }

    @PostMapping("/create/new/product/sub/category/by/product/categoryId/{category_id}")
    @Operation(
            summary = "Create a List of new Product SubCategory by Product CategoryId REST API",
            description = "This API creates a List(Optional) of new Product SubCategory by Product CategoryId",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product SubCategory created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to create Product SubCategory")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductSubCategoryResponse>>> createNewProductSubCategoryByCategoryId(@PathVariable Long category_id, @RequestBody List<ProductSubCategoryRequest> productSubCategoryRequest) {
        try {
            List<ProductSubCategoryResponse> response = productEntryService.createProductSubCategory(category_id, productSubCategoryRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "ProductSubCategory created successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating ProductSubCategory", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create ProductSubCategory", null));
        }

    }

    @PostMapping("/create/new/product/type/by/product/sub/categoryId/{sub_category_id}")
    @Operation(
            summary = "Create a List of new Product Type by Product SubCategoryId REST API",
            description = "This API creates a List(Optional) of new Product Type by Product SubCategoryId",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product ProductType created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to create Product ProductType")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductTypeResponse>>> createNewProductTypeBySubCategoryId(@PathVariable Long sub_category_id, @RequestBody List<ProductTypeRequest> productTypeRequest) {
        try {
            List<ProductTypeResponse> response = productEntryService.createProductType(sub_category_id, productTypeRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "ProductType created successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error creating ProductType", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create ProductType", null));
        }
    }

    @GetMapping("/get/all/product/category")
    @Operation(
            summary = "Get a List of Product Category REST API",
            description = "This API Gets a List of Product Category",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Product Category Retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve List of Product Category")
            }
    )
    public ResponseEntity<ApiResponse<List<ProductCategoryResponse>>> getAllProductCategory() {
        try {
            List<ProductCategoryResponse> response = productEntryService.getListOfProductCategory();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of Product Category Retrieved successfully", response));
        } catch (Exception e) {
            log.error("Error retrieving List of Product Category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve List of Product Category", null));
        }
    }

    @GetMapping("/get/all/product/sub/categories/by/category/name/{category_name}")
    @Operation(
            summary = "Get a List of Product Sub Categories By Category Name REST API",
            description = "This API Gets a List of Product Sub Categories By Category Name",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Product Categories Retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve List of Product Categories")
            }
    )
    public ResponseEntity<ApiResponse<CategoryAndSubCategoryResponse>> getAllProductSubCategoriesByCategoryName(@PathVariable String category_name) {
        try {
            CategoryAndSubCategoryResponse response = productEntryService.getListOfAllSubCategoriesByCategory(category_name);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of Product Categories Retrieved successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error retrieving List of Product Categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve List of Product Categories", null));
        }
    }

    @GetMapping("/get/all/product/type/by/sub/category/name/{sub_category_name}")
    @Operation(
            summary = "Get a List of Product Types By SubCategory Name REST API",
            description = "This API Gets a List of Product Types By SubCategory Name",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of Product Types Retrieved successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Failed to retrieve List of Product Types")
            }
    )
    public ResponseEntity<ApiResponse<SubCategoryAndProductTypeResponse>> getAllProductTypeBySubCategoryName(@PathVariable String sub_category_name) {
        try {
            SubCategoryAndProductTypeResponse response = productEntryService.getListOfAllProductTypesBySubCategory(sub_category_name);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of Product Types Retrieved successfully", response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error retrieving List of Product Types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve List of Product Types", null));
        }
    }
}
