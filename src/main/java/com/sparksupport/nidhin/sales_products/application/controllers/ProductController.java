package com.sparksupport.nidhin.sales_products.application.controllers;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.ProductService;
import com.sparksupport.nidhin.sales_products.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto productRequest) {
        Product savedProduct = productService.addProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDto updatedProductRequest) {
        Product updatedProduct = productService.updateProduct(id, updatedProductRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double totalRevenue = productService.getTotalRevenue();
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }

    @GetMapping("/revenue/{id}")
    public ResponseEntity<Double> getRevenueByProduct(@PathVariable int id) {
        Double revenueByProduct = productService.getRevenueByProduct(id);
        return new ResponseEntity<>(revenueByProduct, HttpStatus.OK);
    }
}
