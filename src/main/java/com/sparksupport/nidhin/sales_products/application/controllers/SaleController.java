package com.sparksupport.nidhin.sales_products.application.controllers;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.SaleDto;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.SaleService;
import com.sparksupport.nidhin.sales_products.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<Page<Sale>> getAllSales(Pageable pageable) {
        Page<Sale> sales = saleService.getAllSales(pageable);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable int id) {
        Sale sale = saleService.getSaleById(id);
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Sale> addSale(@Valid @RequestBody SaleDto saleRequest) {
        Sale savedSale = saleService.addSale(saleRequest);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable int id, @Valid @RequestBody SaleDto updatedSaleRequest) {
        Sale updatedSale = saleService.updateSale(id, updatedSaleRequest);
        return new ResponseEntity<>(updatedSale, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable int id) {
        boolean deleted = saleService.deleteSale(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
