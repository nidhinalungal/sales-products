package com.sparksupport.nidhin.sales_products.application.utilities;

public enum RoleName {
    ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");


    private String value;

    public String getValue() {
        return value;
    }

    RoleName(String value) {
        this.value = value;
    }
}