package com.softnet.shoplife.services;

import com.softnet.shoplife.dto.request.ProProductRequest;
import com.softnet.shoplife.dto.request.UnProProductRequest;
import com.softnet.shoplife.dto.request.ProductRequestWrapper;
import com.softnet.shoplife.dto.request.WarehouseRequest;
import com.softnet.shoplife.dto.responses.ProProductResponse;
import com.softnet.shoplife.dto.responses.UnProProductResponse;
import com.softnet.shoplife.dto.responses.WarehouseResponse;

import java.util.List;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest);
    List<WarehouseResponse> getAllWarehouses();
    WarehouseResponse getWarehouseById(Long id);
    WarehouseResponse updateWarehouse(Long id, WarehouseRequest warehouse);
    void deleteWarehouse(Long id);
    List<UnProProductResponse> getUnprocessedProductsByWarehouseId(Long id);
    UnProProductResponse getUnprocessedProductByWarehouseIdAndProductId(Long warehouseId, Long productId);
    List<UnProProductResponse> addUnprocessedProductsToWarehouse(Long warehouseId, ProductRequestWrapper products);
    List<UnProProductResponse> updateListOfUnprocessedProductsInWarehouse(Long warehouseId, List<UnProProductRequest> products);
    void removeUnprocessedProductsFromWarehouse(Long warehouseId, List<Long> productIds);

    List<ProProductResponse> getProcessedProductsByWarehouseId(Long id);
    ProProductResponse getProcessedProductByWarehouseIdAndProductId(Long warehouseId, Long productId);
    List<ProProductResponse> addProcessedProductsToWarehouse(Long warehouseId, List<ProProductRequest> products);
    List<ProProductResponse> updateListOfProcessedProductsInWarehouse(Long warehouseId, List<ProProductRequest> products);
    void removeProcessedProductsFromWarehouse(Long warehouseId, List<Long> productIds);

    Long totalWarehouseLocation();

    Long totalInventoryUnit();
}