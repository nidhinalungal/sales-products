package com.sparksupport.nidhin.sales_products.application.service.constants;

public class Constants {
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

    public static final String PASSWORD_MESSAGE = "Password must be at least 8 characters long, " +
            "contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";

}
