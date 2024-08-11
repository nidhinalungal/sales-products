package com.sparksupport.nidhin.sales_products.application.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class SaleDto {

    private int id;

    @NotNull(message = "ProductId is mandatory")
    private int productId;

    @NotNull(message = "quantity is mandatory")
    private int quantity;

    @NotNull(message = "Sale Date is mandatory")
    private Date saleDate;
}
