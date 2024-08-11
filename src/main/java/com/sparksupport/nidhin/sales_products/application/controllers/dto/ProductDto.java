package com.sparksupport.nidhin.sales_products.application.controllers.dto;

import com.sparksupport.nidhin.sales_products.domain.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private int id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @Min(value = 0, message = "Quantity must be zero or positive")
    private int quantity;

    private List<@Valid SaleDto> sales;
}
