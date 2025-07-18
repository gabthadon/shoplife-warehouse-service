package com.softnet.shoplife.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softnet.shoplife.dto.request.ProductCategoryRequest;
import com.softnet.shoplife.dto.request.ProductSubCategoryRequest;
import com.softnet.shoplife.dto.request.ProductTypeRequest;
import com.softnet.shoplife.dto.responses.*;
import com.softnet.shoplife.entity.ProductCategory;
import com.softnet.shoplife.entity.ProductSubCategory;
import com.softnet.shoplife.entity.ProductType;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.repository.ProductCategoryRepository;
import com.softnet.shoplife.repository.ProductSubCategoryRepository;
import com.softnet.shoplife.repository.ProductTypeRepository;
import com.softnet.shoplife.services.ProductEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEntryServiceImpl implements ProductEntryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductSubCategoryRepository productSubCategoryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    @Override
    public List<ProductCategoryResponse> createProductCategory(List<ProductCategoryRequest> productCategoryRequest) {
        List<ProductCategoryResponse> createdCategories = new ArrayList<>();
        for (ProductCategoryRequest request : productCategoryRequest) {
            ProductCategory category = new ProductCategory();
            category.setName(request.getName());
            ProductCategory savedCategory = productCategoryRepository.save(category);
            createdCategories.add(modelMapper.map(savedCategory, ProductCategoryResponse.class));
        }
        log.info("Successfully Created product categories: {}", createdCategories);
        return createdCategories;
    }

    @Override
    public List<ProductCategoryResponse> getListOfProductCategory() {
        List<ProductCategory> allCategories = productCategoryRepository.findAll();
        List<ProductCategoryResponse> categoryResponses = allCategories.stream()
                .map(category -> modelMapper.map(category, ProductCategoryResponse.class))
                .collect(Collectors.toList());
        log.info("Retrieved list of product categories: {}", categoryResponses);
        return categoryResponses;
    }

    @Override
    public List<ProductSubCategoryResponse> createProductSubCategory(Long categoryId, List<ProductSubCategoryRequest> productSubCategoryRequest) {
        List<ProductSubCategoryResponse> createdSubCategories = new ArrayList<>();
        ProductCategory category = productCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        for (ProductSubCategoryRequest request : productSubCategoryRequest) {
            ProductSubCategory subCategory = new ProductSubCategory();
            subCategory.setName(request.getName());
            subCategory.setProductCategory(category);
            ProductSubCategory savedSubCategory = productSubCategoryRepository.save(subCategory);
            createdSubCategories.add(modelMapper.map(savedSubCategory, ProductSubCategoryResponse.class));
        }
        log.info("Successfully Created product sub categories: {}", createdSubCategories);
        return createdSubCategories;    }

    @Override
    public List<ProductTypeResponse> createProductType(Long subCategoryId, List<ProductTypeRequest> productTypeRequest) {
        List<ProductTypeResponse> createdTypes = new ArrayList<>();
        ProductSubCategory subCategory = productSubCategoryRepository.findById(subCategoryId).orElseThrow(() -> new NotFoundException("SubCategory not found"));
        for (ProductTypeRequest request : productTypeRequest) {
            ProductType type = new ProductType();
            type.setName(request.getName());
            type.setProductSubCategory(subCategory);
            ProductType savedType = productTypeRepository.save(type);
            createdTypes.add(modelMapper.map(savedType, ProductTypeResponse.class));
        }
        log.info("Successfully Created product types: {}", createdTypes);
        return createdTypes;
    }

    @Override
    public CategoryAndSubCategoryResponse getListOfAllSubCategoriesByCategory(String categoryName) {
        ProductCategory category = productCategoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        List<ProductSubCategory> subCategories = productSubCategoryRepository.findByProductCategory(category);
        ProductCategoryResponse categoryResponse = modelMapper.map(category, ProductCategoryResponse.class);
        List<ProductSubCategoryResponse> subCategoryResponses = subCategories.stream()
                .map(subCategory -> modelMapper.map(subCategory, ProductSubCategoryResponse.class))
                .collect(Collectors.toList());
        log.info("Retrieved list of sub categories: {}", subCategoryResponses);
        return new CategoryAndSubCategoryResponse(categoryResponse, subCategoryResponses);
    }

    @Override
    public SubCategoryAndProductTypeResponse getListOfAllProductTypesBySubCategory(String subCategoryName) {
        ProductSubCategory subCategory = productSubCategoryRepository.findByNameIgnoreCase(subCategoryName)
                .orElseThrow(() -> new NotFoundException("SubCategory not found"));
        List<ProductType> productTypes = productTypeRepository.findByProductSubCategory(subCategory);
        ProductSubCategoryResponse subCategoryResponse = modelMapper.map(subCategory, ProductSubCategoryResponse.class);
        List<ProductTypeResponse> productTypeResponses = productTypes.stream()
                .map(productType -> modelMapper.map(productType, ProductTypeResponse.class))
                .collect(Collectors.toList());
        log.info("Retrieved list of product types: {}", productTypeResponses);
        return new SubCategoryAndProductTypeResponse(subCategoryResponse, productTypeResponses);
    }
}
