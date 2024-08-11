package com.sparksupport.nidhin.sales_products.application.service.interfaces;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import com.sparksupport.nidhin.sales_products.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Product getProductById(int id);
    Product addProduct(ProductDto productRequest);
    Product updateProduct(int id, ProductDto updatedProductRequest);
    boolean deleteProduct(int id);
    // new methods
    double getTotalRevenue();
    double getRevenueByProduct(int productId);
}
