package com.sparksupport.nidhin.sales_products.application.controllers;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.UserRegistrationDto;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String test() {
        return "hello from user controller";
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDto> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserRegistrationDto response = userService.registerUser(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
