package com.sparksupport.nidhin.sales_products.application.controllers.dto;


import com.sparksupport.nidhin.sales_products.application.service.constants.Constants;
import com.sparksupport.nidhin.sales_products.application.utilities.RoleName;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
public class UserRegistrationDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp= Constants.PASSWORD_PATTERN, message = Constants.PASSWORD_MESSAGE)
    private String password;

    @NotBlank(message = "Full name is mandatory")
    @Size(min = 1, message = "Full name cannot be empty")
    private String fullName;

    @NotEmpty(message = "Role names cannot be empty")
    private List<RoleName> roleNames;
}
