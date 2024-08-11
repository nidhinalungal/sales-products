package com.sparksupport.nidhin.sales_products.domain;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private List<Sale> sales;

    public Product(ProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.quantity = productDto.getQuantity();
//        this.sales = productDto.getSales().stream().map(Sale::create).collect(Collectors.toList());
    }

    public static Product create(ProductDto productDto) {
        return new Product(productDto);
    }
}
