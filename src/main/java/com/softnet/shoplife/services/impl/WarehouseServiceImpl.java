package com.softnet.shoplife.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softnet.shoplife.dto.request.*;
import com.softnet.shoplife.dto.responses.ProProductResponse;
import com.softnet.shoplife.dto.responses.UnProProductResponse;
import com.softnet.shoplife.dto.responses.WarehouseResponse;
import com.softnet.shoplife.entity.*;
import com.softnet.shoplife.enums.ProductState;
import com.softnet.shoplife.enums.StockStatus;
import com.softnet.shoplife.exceptions.InvalidRequestException;
import com.softnet.shoplife.exceptions.NotFoundException;
import com.softnet.shoplife.exceptions.WarehouseCreationException;
import com.softnet.shoplife.repository.*;
import com.softnet.shoplife.services.WarehouseService;
import com.softnet.shoplife.utils.IDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.softnet.shoplife.constant.IdValues.STOCK_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final UnProProductRepository unProProductRepository;
    private final ProProductRepository proProductRepository;
    private final ProductImageRepository productImageRepository;
    private final InFlowOutFlowRepository inFlowOutFlowRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;


    /**
     * This code is a method that creates a warehouse inventory
     * based on the given warehouse request. It involves mapping the
     * request to the warehouse inventory, saving the location and
     * products, and then mapping the saved warehouse inventory
     * to a response. It also handles exceptions and logs errors.
     * @param  warehouseRequest   the request containing the details of the warehouse to be created
     * @return                    the response containing the details of the created warehouse
     */
    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest) {
        if (warehouseInventoryRepository.existsByWarehouseName(warehouseRequest.getWarehouseName())) {
            throw new InvalidRequestException("Warehouse with name " + warehouseRequest.getWarehouseName() + " already exists");
        }

        WarehouseInventory warehouseInventory = new WarehouseInventory();
        warehouseInventory.setWarehouseName(warehouseRequest.getWarehouseName());
        warehouseInventory.setMaxCapacity(warehouseRequest.getMaxCapacity());
        warehouseInventory.setImage(warehouseRequest.getImage());
        warehouseInventory.setContactPhone(warehouseRequest.getContactPhone());
        warehouseInventory.setContactEmail(warehouseRequest.getContactEmail());
        warehouseInventory.setWarehouseLocation(warehouseRequest.getWarehouseLocation());

        WarehouseInventory savedWarehouseInventory = warehouseInventoryRepository.save(warehouseInventory);

        List<ProcessedProducts> savedProcessedProducts = new ArrayList<>();
        List<UnprocessedProduct> savedUnprocessedProducts = new ArrayList<>();
//        List<InFlowOutFlow> saveInFlowUnit = new ArrayList<>();

        // Create and save UnprocessedProducts
        if (warehouseRequest.getUnprocessedProducts() != null && !warehouseRequest.getUnprocessedProducts().isEmpty()) {
            for (UnProProductRequest unProProductRequest : warehouseRequest.getUnprocessedProducts()) {
                Long totalQuantity = warehouseRequest.getUnprocessedProducts().get(0).getTotalQuantity();
                for (int i = 0; i < totalQuantity; i++) {
                    UnprocessedProduct unprocessedProduct = new UnprocessedProduct();
                    unprocessedProduct.setCategory(unProProductRequest.getCategory());
                    unprocessedProduct.setSubCategory(unProProductRequest.getSubCategory());
                    unprocessedProduct.setProductName(unProProductRequest.getProductName());
                    unprocessedProduct.setPurchasedPrice(unProProductRequest.getPurchasedPrice());
                    unprocessedProduct.setUnitWeight(unProProductRequest.getUnitWeight());
                    unprocessedProduct.setStorageType(unProProductRequest.getStorageType());
                    unprocessedProduct.setStorageLife(unProProductRequest.getStorageLife());
                    unprocessedProduct.setStockId(IDGenerator.generateID(STOCK_ID));
                    unprocessedProduct.setAvailabilityStatus(StockStatus.IN_STOCK);
                    unprocessedProduct.setProductState(ProductState.UNMOVED);
                    unprocessedProduct.setSupplier(unProProductRequest.getSupplier());
                    unprocessedProduct.setPurchaseDate(unProProductRequest.getPurchaseDate());
                    unprocessedProduct.setWarehouseInventory(savedWarehouseInventory);
                    savedUnprocessedProducts.add(unProProductRepository.save(unprocessedProduct));

                    // Save InFlowOutFlow for each unprocessed product
                    InFlowOutFlow inFlowOutFlow = new InFlowOutFlow();
                    inFlowOutFlow.setInFlowUnitQty(unprocessedProduct.getUnitWeight());
                    inFlowOutFlow.setWarehouseInventory(savedWarehouseInventory);
                    inFlowOutFlowRepository.save(inFlowOutFlow);
//                    saveInFlowUnit.add(inFlowOutFlowRepository.save(inFlowOutFlow));
                }
            }
        }
        // Create and save ProcessedProducts
        if (warehouseRequest.getProcessedProducts() != null && !warehouseRequest.getProcessedProducts().isEmpty()) {
            for (ProProductRequest processedProductRequest : warehouseRequest.getProcessedProducts()) {
                Long totalQuantity = warehouseRequest.getProcessedProducts().get(0).getTotalQuantity();
                for (int i = 0; i < totalQuantity; i++) {
                    ProcessedProducts processedProduct = new ProcessedProducts();
                    processedProduct.setCategory(processedProductRequest.getCategory());
                    processedProduct.setSubCategory(processedProductRequest.getSubCategory());
                    processedProduct.setProductName(processedProductRequest.getProductName());
                    processedProduct.setDescription(processedProductRequest.getDescription());
                    processedProduct.setSellingPrice(processedProductRequest.getSellingPrice());
                    processedProduct.setUnitWeight(processedProductRequest.getUnitWeight());
                    processedProduct.setStockId(IDGenerator.generateID(STOCK_ID));
                    processedProduct.setAvailabilityStatus(StockStatus.IN_STOCK);
                    processedProduct.setAddedBy(processedProductRequest.getAddedBy());
                    processedProduct.setAddedDate(LocalDate.now());
                    processedProduct.setWarehouseInventory(savedWarehouseInventory);
                    savedProcessedProducts.add(proProductRepository.save(processedProduct));
                    List<ProductImages> savedProductImages = new ArrayList<>();
                    for(ProductImageRequest productImageRequest : processedProductRequest.getProductImages()){
                        ProductImages productImage = new ProductImages();
                        productImage.setImageUrl(productImageRequest.getImageUrl());
                        productImage.setProcessedProducts(processedProduct);
                        savedProductImages.add(productImageRepository.save(productImage));
                    }
                    processedProduct.setProductImages(savedProductImages);
                }
            }
        }
        // Update the warehouse inventory with the saved products
        savedWarehouseInventory.setUnprocessedProducts(savedUnprocessedProducts);
        savedWarehouseInventory.setProcessedProducts(savedProcessedProducts);
        // Map the saved warehouse inventory to response
        WarehouseResponse warehouseResponse = modelMapper.map(savedWarehouseInventory, WarehouseResponse.class);
        warehouseResponse.setUnprocessedProducts(savedUnprocessedProducts.stream()
                .map(product -> modelMapper.map(product, UnProProductResponse.class)).collect(Collectors.toList()));
        warehouseResponse.setProcessedProducts(savedProcessedProducts.stream()
                .map(product -> modelMapper.map(product, ProProductResponse.class)).collect(Collectors.toList()));
        log.info("Successfully Created Warehouse For: {}", warehouseRequest.getWarehouseName());
        return warehouseResponse;
    }
    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        try {
            List<WarehouseInventory> allWarehouseInventories = warehouseInventoryRepository.findAll();
            return allWarehouseInventories.stream()
                    .map(warehouseInventory -> {
                        WarehouseResponse warehouseResponse = modelMapper.map(warehouseInventory, WarehouseResponse.class);
                        warehouseResponse.setUnprocessedProducts(warehouseInventory.getUnprocessedProducts().stream()
                                .map(product -> modelMapper.map(product, UnProProductResponse.class)).collect(Collectors.toList()));
                        return warehouseResponse;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while getting all warehouses: " + e.getMessage());
            throw new NotFoundException("Error occurred while getting all warehouses");
        }
    }

    @Override
    public WarehouseResponse getWarehouseById(Long id) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(id);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                WarehouseResponse warehouseResponse = modelMapper.map(warehouseInventory, WarehouseResponse.class);
                warehouseResponse.setUnprocessedProducts(warehouseInventory.getUnprocessedProducts().stream()
                        .map(product -> modelMapper.map(product, UnProProductResponse.class)).collect(Collectors.toList()));
                // Ensure warehouseId is properly set
                warehouseResponse.setId(warehouseInventory.getId());
                return warehouseResponse;
            } else {
                // Handle not found scenario
                throw new NotFoundException("Warehouse not found with id: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while getting warehouse by id: " + e.getMessage());
            throw new NotFoundException("Error occurred while getting warehouse by id");
        }
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest warehouse) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(id);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                // Update warehouse details
                if (warehouse.getWarehouseName() != null) {
                    warehouseInventory.setWarehouseName(warehouse.getWarehouseName());
                }
                if (warehouse.getMaxCapacity() != null) {
                    warehouseInventory.setMaxCapacity(warehouse.getMaxCapacity());
                }
                if (warehouse.getImage() != null) {
                    warehouseInventory.setImage(warehouse.getImage());
                }
                if (warehouse.getContactPhone() != null) {
                    warehouseInventory.setContactPhone(warehouse.getContactPhone());
                }
                if (warehouse.getContactEmail() != null) {
                    warehouseInventory.setContactEmail(warehouse.getContactEmail());
                }
                if (warehouse.getWarehouseLocation() != null) {
                    warehouseInventory.setWarehouseLocation(warehouse.getWarehouseLocation());
                }

                // Save the updated WarehouseInventory
                WarehouseInventory updatedWarehouseInventory = warehouseInventoryRepository.save(warehouseInventory);
                // Mapping the updated WarehouseInventory to WarehouseResponse
                WarehouseResponse warehouseResponse = modelMapper.map(updatedWarehouseInventory, WarehouseResponse.class);
                warehouseResponse.setUnprocessedProducts(updatedWarehouseInventory.getUnprocessedProducts().stream()
                        .map(product -> modelMapper.map(product, UnProProductResponse.class)).collect(Collectors.toList()));
                log.info("Successfully updated warehouse with id: {}", id);
                return warehouseResponse;
            } else {
                // Handle not found scenario
                throw new NotFoundException("Warehouse not found with id: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating warehouse with id " + id + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while updating warehouse");
        }
    }

    @Override
    public void deleteWarehouse(Long id) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(id);
            if (warehouseInventoryOptional.isPresent()) {
                warehouseInventoryRepository.deleteById(id);
                log.info("Successfully deleted warehouse with id: {}", id);
            } else {
                // Handle not found scenario
                throw new NotFoundException("Warehouse not found with id: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting warehouse with id " + id + ": " + e.getMessage());
            throw new NotFoundException("Error occurred while deleting warehouse");
        }
    }

    @Override
    public List<UnProProductResponse> getUnprocessedProductsByWarehouseId(Long id) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(id);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<UnprocessedProduct> unprocessedProducts = warehouseInventory.getUnprocessedProducts();
                return unprocessedProducts.stream()
                        .map(product -> modelMapper.map(product, UnProProductResponse.class))
                        .collect(Collectors.toList());
            } else {
                throw new NotFoundException("Warehouse not found with id: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while getting products for warehouse with id " + id + ": " + e.getMessage());
            throw new NotFoundException("Warehouse not found with id: " + id);
        }
    }

    @Override
    public UnProProductResponse getUnprocessedProductByWarehouseIdAndProductId(Long warehouseId, Long productId) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                Optional<UnprocessedProduct> productOptional = warehouseInventory.getUnprocessedProducts().stream()
                        .filter(product -> product.getId().equals(productId))
                        .findFirst();
                if (productOptional.isPresent()) {
                    UnprocessedProduct unprocessedProduct = productOptional.get();
                    return modelMapper.map(unprocessedProduct, UnProProductResponse.class);
                } else {
                    throw new NotFoundException("UnprocessedProduct not found with id: " + productId + " in warehouse with id: " + warehouseId);
                }
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while getting product for warehouse with id " + warehouseId + " and product id " + productId + ": " + e.getMessage());
            throw new NotFoundException("Error occurred while getting product for warehouse");
        }
    }

    @Override
    public List<UnProProductResponse> addUnprocessedProductsToWarehouse(Long warehouseId, ProductRequestWrapper products) {
        try {
            // Check if the warehouse exists
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<UnProProductResponse> savedProducts = new ArrayList<>();
//                List<InFlowOutFlow> saveInFlowUnit = new ArrayList<>();
                for (UnProProductRequest unProProductRequest : products.getProducts()) {
                    Long totalQuantity = unProProductRequest.getTotalQuantity();
                    for (int i = 0; i < totalQuantity; i++) {
                        UnprocessedProduct unprocessedProduct = mapUnprocessedProductRequestToUnprocessedProduct(unProProductRequest);
                        unprocessedProduct.setWarehouseInventory(warehouseInventory);
                        UnprocessedProduct savedUnprocessedProduct = unProProductRepository.save(unprocessedProduct);
                        savedProducts.add(modelMapper.map(savedUnprocessedProduct, UnProProductResponse.class));
                        // Save InFlowOutFlow for each unprocessed product
                        InFlowOutFlow inFlowOutFlow = new InFlowOutFlow();
                        inFlowOutFlow.setInFlowUnitQty(unprocessedProduct.getUnitWeight());
                        inFlowOutFlow.setWarehouseInventory(warehouseInventory);
                        inFlowOutFlowRepository.save(inFlowOutFlow);
//                        saveInFlowUnit.add(inFlowOutFlowRepository.save(inFlowOutFlow));
                    }
                }
                return savedProducts;
            } else {
                // Throw an exception if the warehouse is not found
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            // Log and throw an exception if an error occurs
            log.error("Error occurred while adding products to warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while adding products to warehouse");
        }
    }

    @Override
    public List<UnProProductResponse> updateListOfUnprocessedProductsInWarehouse(Long warehouseId, List<UnProProductRequest> products) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<UnProProductResponse> updatedProducts = new ArrayList<>();
                for (UnProProductRequest unProProductRequest : products) {
                    Optional<UnprocessedProduct> existingProductOptional = warehouseInventory.getUnprocessedProducts().stream()
                            .filter(product -> product.getId().equals(unProProductRequest.getId()))
                            .findFirst();
                    if (existingProductOptional.isPresent()) {
                        UnprocessedProduct existingUnprocessedProduct = existingProductOptional.get();
                        if (unProProductRequest.getCategory() != null) {
                            existingUnprocessedProduct.setCategory(unProProductRequest.getCategory());
                        }
                        if (unProProductRequest.getSubCategory() != null) {
                            existingUnprocessedProduct.setSubCategory(unProProductRequest.getSubCategory());
                        }
                        if (unProProductRequest.getProductName() != null) {
                            existingUnprocessedProduct.setProductName(unProProductRequest.getProductName());
                        }
                        if (unProProductRequest.getPurchasedPrice() != null) {
                            existingUnprocessedProduct.setPurchasedPrice(unProProductRequest.getPurchasedPrice());
                        }
                        if (unProProductRequest.getUnitWeight() != null) {
                            existingUnprocessedProduct.setUnitWeight(unProProductRequest.getUnitWeight());
                        }
                        if (unProProductRequest.getStorageType() != null) {
                            existingUnprocessedProduct.setStorageType(unProProductRequest.getStorageType());
                        }
                        if (unProProductRequest.getStorageLife() != null) {
                            existingUnprocessedProduct.setStorageLife(unProProductRequest.getStorageLife());
                        }
                        if (unProProductRequest.getAvailabilityStatus() != null) {
                            existingUnprocessedProduct.setAvailabilityStatus(unProProductRequest.getAvailabilityStatus());
                        }
                        if (unProProductRequest.getProductState() != null) {
                            existingUnprocessedProduct.setProductState(unProProductRequest.getProductState());
                        }
                        if (unProProductRequest.getPurchaseDate() != null) {
                            existingUnprocessedProduct.setPurchaseDate(unProProductRequest.getPurchaseDate());
                        }
                        if (unProProductRequest.getSupplier() != null) {
                            existingUnprocessedProduct.setSupplier(unProProductRequest.getSupplier());
                        }
                        UnprocessedProduct updatedUnprocessedProduct = unProProductRepository.save(existingUnprocessedProduct);
                        updatedProducts.add(modelMapper.map(updatedUnprocessedProduct, UnProProductResponse.class));
                    }
                }
                return updatedProducts;
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating products in warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while updating products in warehouse");
        }
    }


    @Override
    public void removeUnprocessedProductsFromWarehouse(Long warehouseId, List<Long> productIds) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                for (Long productId : productIds) {
                    unProProductRepository.deleteById(productId);
                }
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while removing products from warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while removing products from warehouse");
        }
    }

    @Override
    public List<ProProductResponse> getProcessedProductsByWarehouseId(Long id) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(id);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<ProcessedProducts> processedProducts = warehouseInventory.getProcessedProducts();
                return processedProducts.stream()
                        .map(product -> modelMapper.map(product, ProProductResponse.class))
                        .collect(Collectors.toList());
            } else {
                throw new NotFoundException("Warehouse not found with id: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while getting processed products for warehouse with id " + id + ": " + e.getMessage());
            throw new NotFoundException("Warehouse not found with id: " + id);
        }
    }

    @Override
    public ProProductResponse getProcessedProductByWarehouseIdAndProductId(Long warehouseId, Long productId) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                Optional<ProcessedProducts> productOptional = warehouseInventory.getProcessedProducts().stream()
                        .filter(product -> product.getId().equals(productId))
                        .findFirst();
                if (productOptional.isPresent()) {
                    ProcessedProducts unprocessedProduct = productOptional.get();
                    return modelMapper.map(unprocessedProduct, ProProductResponse.class);
                } else {
                    throw new NotFoundException("ProcessedProduct not found with id: " + productId + " in warehouse with id: " + warehouseId);
                }
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while getting processed product for warehouse with id " + warehouseId + " and product id " + productId + ": " + e.getMessage());
            throw new NotFoundException("Error occurred while getting product for warehouse");
        }
    }

    @Override
    public List<ProProductResponse> addProcessedProductsToWarehouse(Long warehouseId, List<ProProductRequest> products) {
        try {
            // Check if the warehouse exists
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<ProProductResponse> savedProducts = new ArrayList<>();
                for (ProProductRequest proProductRequest : products) {
                    Long totalQuantity = proProductRequest.getTotalQuantity();
                    for (int i = 0; i < totalQuantity; i++) {
                        ProcessedProducts processedProduct = mapProcessedProductRequestToProcessedProduct(proProductRequest);
                        processedProduct.setWarehouseInventory(warehouseInventory);
                        ProcessedProducts savedProcessedProduct = proProductRepository.save(processedProduct);
                        List<ProductImages> savedProductImages = new ArrayList<>();
                        for(ProductImageRequest productImageRequest : proProductRequest.getProductImages()){
                            ProductImages productImage = new ProductImages();
                            productImage.setImageUrl(productImageRequest.getImageUrl());
                            productImage.setProcessedProducts(processedProduct);
                            savedProductImages.add(productImageRepository.save(productImage));
                        }
                        processedProduct.setProductImages(savedProductImages);
                        savedProducts.add(modelMapper.map(savedProcessedProduct, ProProductResponse.class));
                    }
                }
                return savedProducts;
            } else {
                // Throw an exception if the warehouse is not found
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            // Log and throw an exception if an error occurs
            log.error("Error occurred while adding processed products to warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while adding processed products to warehouse");
        }
    }

    @Override
    public List<ProProductResponse> updateListOfProcessedProductsInWarehouse(Long warehouseId, List<ProProductRequest> products) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                List<ProProductResponse> updatedProducts = new ArrayList<>();
                for (ProProductRequest proProductRequest : products) {
                    Optional<ProcessedProducts> existingProductOptional = warehouseInventory.getProcessedProducts().stream()
                            .filter(product -> product.getId().equals(proProductRequest.getId()))
                            .findFirst();
                    if (existingProductOptional.isPresent()) {
                        ProcessedProducts existingProcessedProduct = existingProductOptional.get();
                        if (proProductRequest.getCategory() != null) {
                            existingProcessedProduct.setCategory(proProductRequest.getCategory());
                        }
                        if (proProductRequest.getSubCategory() != null) {
                            existingProcessedProduct.setSubCategory(proProductRequest.getSubCategory());
                        }
                        if (proProductRequest.getProductName() != null) {
                            existingProcessedProduct.setProductName(proProductRequest.getProductName());
                        }
                        if (proProductRequest.getDescription() != null) {
                            existingProcessedProduct.setDescription(proProductRequest.getDescription());
                        }
//                        if (proProductRequest.getImage() != null) {
//                            existingProcessedProduct.setImage(proProductRequest.getImage());
//                        }
                        if (proProductRequest.getSellingPrice() != null) {
                            existingProcessedProduct.setSellingPrice(proProductRequest.getSellingPrice());
                        }
                        if (proProductRequest.getUnitWeight() != null) {
                            existingProcessedProduct.setUnitWeight(proProductRequest.getUnitWeight());
                        }
                        if (proProductRequest.getAvailabilityStatus() != null) {
                            existingProcessedProduct.setAvailabilityStatus(proProductRequest.getAvailabilityStatus());
                        }
                        if (proProductRequest.getAddedBy() != null) {
                            existingProcessedProduct.setAddedBy(proProductRequest.getAddedBy());
                        }
                        List<ProductImages> savedProductImages = new ArrayList<>();
                        for (ProductImageRequest productImageRequest : proProductRequest.getProductImages()) {
                            Optional<ProductImages> existingProductImage = existingProcessedProduct.getProductImages().stream()
                                    .filter(product -> product.getId().equals(productImageRequest.getId()))
                                    .findFirst();
                            if (existingProductImage.isPresent()) {
                                ProductImages productImage = existingProductImage.get();
                                if (productImageRequest.getImageUrl() != null) {
                                    productImage.setImageUrl(productImageRequest.getImageUrl());
                                }
//                                productImage.setProcessedProducts(existingProcessedProduct);
                                // Check if the saved product images already contain the updated image
                                boolean isImageAlreadySaved = savedProductImages.stream()
                                        .anyMatch(savedImage -> savedImage.getId().equals(productImage.getId()));
                                if (!isImageAlreadySaved) {
                                    savedProductImages.add(productImageRepository.save(productImage));
                                }
                            }
                        }
                        existingProcessedProduct.getProductImages().clear();
                        existingProcessedProduct.getProductImages().addAll(savedProductImages);
                        ProcessedProducts updatedProcessedProduct = proProductRepository.save(existingProcessedProduct);
                        updatedProducts.add(modelMapper.map(updatedProcessedProduct, ProProductResponse.class));
                    }
                }
                return updatedProducts;
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating processed products in warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while updating processed products in warehouse");
        }
    }


    @Override
    public void removeProcessedProductsFromWarehouse(Long warehouseId, List<Long> productIds) {
        try {
            Optional<WarehouseInventory> warehouseInventoryOptional = warehouseInventoryRepository.findById(warehouseId);
            if (warehouseInventoryOptional.isPresent()) {
                WarehouseInventory warehouseInventory = warehouseInventoryOptional.get();
                for (Long productId : productIds) {
                    proProductRepository.deleteById(productId);
                }
            } else {
                throw new NotFoundException("Warehouse not found with id: " + warehouseId);
            }
        } catch (Exception e) {
            log.error("Error occurred while removing processed products from warehouse with id " + warehouseId + ": " + e.getMessage());
            throw new WarehouseCreationException("Error occurred while removing products from warehouse");
        }
    }

    @Override
    public Long totalWarehouseLocation() {
        try {
            return warehouseInventoryRepository.getSumOfWarehouseStateLocation();
        } catch (Exception e) {
            log.error("Error occurred while getting warehouse total state" +  e.getMessage());
            throw new WarehouseCreationException("Error occurred while getting warehouse total state");
        }
    }

    @Override
    public Long totalInventoryUnit() {
        try {
            return warehouseInventoryRepository.getSumOfWarehouseStateLocation();
        } catch (Exception e) {
            log.error("Error occurred while getting warehouse total units" +  e.getMessage());
            throw new WarehouseCreationException("Error occurred while getting warehouse total units");
        }
    }

    private UnprocessedProduct mapUnprocessedProductRequestToUnprocessedProduct(UnProProductRequest unProProductRequest) {
        UnprocessedProduct unprocessedProduct = new UnprocessedProduct();
        unprocessedProduct.setCategory(unProProductRequest.getCategory());
        unprocessedProduct.setSubCategory(unProProductRequest.getSubCategory());
        unprocessedProduct.setProductName(unProProductRequest.getProductName());
        unprocessedProduct.setPurchasedPrice(unProProductRequest.getPurchasedPrice());
        unprocessedProduct.setUnitWeight(unProProductRequest.getUnitWeight());
        unprocessedProduct.setStorageType(unProProductRequest.getStorageType());
        unprocessedProduct.setStorageLife(unProProductRequest.getStorageLife());
        unprocessedProduct.setStockId(IDGenerator.generateID(STOCK_ID));
        unprocessedProduct.setAvailabilityStatus(StockStatus.IN_STOCK);
        unprocessedProduct.setProductState(ProductState.UNMOVED);
        unprocessedProduct.setSupplier(unProProductRequest.getSupplier());
        unprocessedProduct.setPurchaseDate(unProProductRequest.getPurchaseDate());
        return unprocessedProduct;
    }
    private ProcessedProducts mapProcessedProductRequestToProcessedProduct(ProProductRequest proProductRequest) {
        ProcessedProducts processedProducts = new ProcessedProducts();
        processedProducts.setCategory(proProductRequest.getCategory());
        processedProducts.setSubCategory(proProductRequest.getSubCategory());
        processedProducts.setProductName(proProductRequest.getProductName());
        processedProducts.setDescription(proProductRequest.getDescription());
        processedProducts.setSellingPrice(proProductRequest.getSellingPrice());
        processedProducts.setUnitWeight(proProductRequest.getUnitWeight());
        processedProducts.setStockId(IDGenerator.generateID(STOCK_ID));
        processedProducts.setAvailabilityStatus(StockStatus.IN_STOCK);
        processedProducts.setAddedBy(proProductRequest.getAddedBy());
        processedProducts.setAddedDate(LocalDate.now());
        return processedProducts;
    }
}
