package com.sparksupport.nidhin.sales_products.application.service.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(int id) {
        super("Product with id " + id + " not found.");
    }
}