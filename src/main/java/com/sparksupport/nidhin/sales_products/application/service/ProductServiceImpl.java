package com.sparksupport.nidhin.sales_products.application.service;


import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.ProductNotFoundException;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.ProductService;
import com.sparksupport.nidhin.sales_products.domain.Product;
import com.sparksupport.nidhin.sales_products.infrastructure.models.ProductEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.ProductRepository;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<ProductEntity> paginatedProducts = productRepository.findAll(pageable);
        List<Product> products = paginatedProducts.getContent().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(products, pageable, paginatedProducts.getTotalElements());
    }

    @Override
    public Product getProductById(int id) {
        ProductEntity productEntity = productRepository.findByIdWithSales(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product addProduct(ProductDto productRequest) {
        Product product = Product.create(productRequest);
        ProductEntity savedProduct = productRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(savedProduct);
    }

    @Override
    public Product updateProduct(int id, ProductDto updateProductRequest) {
        Product product = getProductById(id);
        updateProductDetails(product, updateProductRequest);
        ProductEntity updatedProduct = productRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(updatedProduct);
    }

    private void updateProductDetails(Product product, ProductDto updateProductRequest) {
        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setPrice(updateProductRequest.getPrice());
        product.setQuantity(updateProductRequest.getQuantity());
    }

    @Override
    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else throw new ProductNotFoundException(id);
    }

    @Override
    public double getTotalRevenue() {
        return productRepository.findAll().stream()
                .flatMap(product -> product.getSales().stream()
                        .map(sale -> sale.getQuantity() * product.getPrice()))
                .mapToDouble(Double::doubleValue)
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
