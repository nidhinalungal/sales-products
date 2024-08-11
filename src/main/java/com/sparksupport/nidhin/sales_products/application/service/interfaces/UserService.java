package com.sparksupport.nidhin.sales_products.application.service.interfaces;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.UserRegistrationDto;

public interface UserService {
    UserRegistrationDto registerUser(UserRegistrationDto registrationDto);
}
