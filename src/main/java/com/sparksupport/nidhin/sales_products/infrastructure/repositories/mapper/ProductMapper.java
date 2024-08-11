package com.sparksupport.nidhin.sales_products.infrastructure.repositories.mapper;

import com.sparksupport.nidhin.sales_products.domain.Product;
import com.sparksupport.nidhin.sales_products.infrastructure.models.ProductEntity;

import java.util.Collections;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product toDomain(ProductEntity entity) {
        return Product.builder().id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .sales(entity.getSales()!= null ?entity.getSales().stream().map(SaleMapper::toDomain).collect(Collectors.toList()): Collections.emptyList())
                .build();
    }
    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
//                .sales(product.getSales().stream().map(SaleMapper::toEntity).collect(Collectors.toList()))
                .build();
    }
}
