package com.softnet.shoplife.exceptions;

public class WarehouseCreationException extends RuntimeException {
    public WarehouseCreationException(String message) {
        super(message);
    }

    public WarehouseCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}