package com.sparksupport.nidhin.sales_products.application.service.interfaces;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.SaleDto;
import com.sparksupport.nidhin.sales_products.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    Page<Sale> getAllSales(Pageable pageable);

    Sale getSaleById(int id);

    Sale addSale(SaleDto saleRequest);

    Sale updateSale(int id, SaleDto updatedSaleRequest);

    boolean deleteSale(int id);
}
