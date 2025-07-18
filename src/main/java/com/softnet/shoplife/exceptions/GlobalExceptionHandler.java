package com.softnet.shoplife.exceptions;

import com.softnet.shoplife.dto.responses.ApiResponse;
import com.softnet.shoplife.utils.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
            InvalidRequestException.class,
            DataIntegrityViolationException.class,
            UnauthorizedAccessException.class,
            ServiceUnavailableException.class,
            WarehouseCreationException.class
    })
    public ResponseEntity<ApiResponse<String>> handleCustomExceptions(RuntimeException ex) {
        ApiResponse<String> response = ApiResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof InvalidRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT;
        } else if (ex instanceof UnauthorizedAccessException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof ServiceUnavailableException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        return new ResponseEntity<>(response, status);
    }

    // Add more exception handlers as needed

}