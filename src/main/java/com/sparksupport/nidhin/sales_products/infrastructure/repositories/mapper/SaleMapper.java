package com.sparksupport.nidhin.sales_products.infrastructure.repositories.mapper;

import com.sparksupport.nidhin.sales_products.domain.Product;
import com.sparksupport.nidhin.sales_products.domain.Sale;
import com.sparksupport.nidhin.sales_products.infrastructure.models.ProductEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.models.SaleEntity;

import java.util.stream.Collectors;

public class SaleMapper {
    public static Sale toDomain(SaleEntity entity) {
        return Sale.builder().id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : 0)
                .quantity(entity.getQuantity())
                .saleDate(entity.getSaleDate())
                .build();
    }
    public static SaleEntity toEntity(Sale sale) {
        return SaleEntity.builder().id(sale.getId())
                .quantity(sale.getQuantity())
                .saleDate(sale.getSaleDate())
                .build();
    }
}
