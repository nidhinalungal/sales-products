package com.sparksupport.nidhin.sales_products.application.service.exceptions;


public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(int id) {
        super("Sale with id " + id + " not found.");
    }
}