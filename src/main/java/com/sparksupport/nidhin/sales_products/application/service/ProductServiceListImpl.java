package com.sparksupport.nidhin.sales_products.application.service;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.ProductNotFoundException;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.ProductService;
import com.sparksupport.nidhin.sales_products.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Primary
@Service
public class ProductServiceListImpl implements ProductService {
    private List<Product> products;

    public ProductServiceListImpl() {
        this.products = new ArrayList<>();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, products.size());
        if (startIndex >= products.size()) {
            return new PageImpl<>(List.of(), pageable, products.size());
        }
        List<Product> paginatedProducts = products.subList(startIndex, endIndex);
        return new PageImpl<>(paginatedProducts, pageable, products.size());
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product addProduct(ProductDto productRequest) {
        Product product = Product.create(productRequest);
        products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(int id, ProductDto updatedProductRequest) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getId() == id) {
                updatedProductRequest.setId(id);
                Product updatedProduct = Product.create(updatedProductRequest);
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        throw new ProductNotFoundException(id);
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean removed = products.removeIf(product -> product.getId() == id);
        if (!removed) {
            throw new ProductNotFoundException(id);
        }
        return true;
    }

    @Override
    public double getTotalRevenue() {
        return products.stream()
                .flatMap(product -> product.getSales().stream())
                .mapToDouble(sale -> sale.getQuantity() * getProductById(sale.getProductId()).getPrice())
                .sum();
    }

    @Override
    public double getRevenueByProduct(int productId) {
        Product product = getProductById(productId);
        if (product.getSales() != null) {
            return product.getSales().stream()
                    .mapToDouble(sale -> sale.getQuantity() * product.getPrice())
                    .sum();
        }
        return 0.0;
    }
}
