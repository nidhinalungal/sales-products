package com.sparksupport.nidhin.sales_products.domain;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.SaleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Sale {
    private int id;
    private int productId;
    private int quantity;
    private Date saleDate;

    public Sale(SaleDto saleDto) {
        this.id = saleDto.getId();
        this.productId = saleDto.getProductId();
        this.quantity = saleDto.getQuantity();
        this.saleDate = saleDto.getSaleDate();
    }

    public static Sale create(SaleDto saleDto) {
        return new Sale(saleDto);
    }
}
