package com.sparksupport.nidhin.sales_products;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.ProductDto;
import com.sparksupport.nidhin.sales_products.application.controllers.dto.SaleDto;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication
public class SalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ProductService productService) {
        return args -> {
            SaleDto sale1 = new SaleDto(1, 1, 5, new Date());
            SaleDto sale2 = new SaleDto(2, 2, 10, new Date());
            SaleDto sale3 = new SaleDto(3, 3, 20, new Date());
            SaleDto sale4 = new SaleDto(4, 3, 10, new Date());


            ProductDto product1 = new ProductDto(1, "Laptop", "High-end gaming laptop", 1500.00, 10, List.of(sale1));
            ProductDto product2 = new ProductDto(2, "Smartphone", "Latest models smartphone", 800.00, 25, List.of(sale2));
            ProductDto product3 = new ProductDto(3, "Headphones", "Noise-cancelling headphones", 200.00, 50, Arrays.asList(sale3, sale4));

            productService.addProduct(product1);
            productService.addProduct(product2);
            productService.addProduct(product3);

            log.info("All products: {}, {}, {}", product1, product2, product3);
        };
    }
}
