package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.request.ProductCategoryRequest;
import com.softnet.shoplife.dto.request.ProductSubCategoryRequest;
import com.softnet.shoplife.dto.request.ProductTypeRequest;
import com.softnet.shoplife.dto.responses.*;

import java.util.List;

public interface ProductEntryService {
    List<ProductCategoryResponse> createProductCategory(List<ProductCategoryRequest> productCategoryRequest);
    List<ProductCategoryResponse> getListOfProductCategory();
    List<ProductSubCategoryResponse> createProductSubCategory(Long categoryId, List<ProductSubCategoryRequest> productSubCategoryRequest);
    List<ProductTypeResponse> createProductType(Long subCategoryId, List<ProductTypeRequest> productTypeRequest);

    CategoryAndSubCategoryResponse getListOfAllSubCategoriesByCategory(String categoryName);

    SubCategoryAndProductTypeResponse getListOfAllProductTypesBySubCategory(String subCategoryName);

}
