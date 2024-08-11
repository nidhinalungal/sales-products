package com.sparksupport.nidhin.sales_products.application.utilities;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class Utils {
    public static ResponseEntity<ExceptionResponse> getResponseEntity(String message, HttpStatus status) {
        log.error(message);
        ExceptionResponse response = new ExceptionResponse(status.value(), message);
        return new ResponseEntity<>(response, status);
    }
}
