package com.sparksupport.nidhin.sales_products.application.service;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.SaleDto;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.InsufficientStockException;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.ProductNotFoundException;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.SaleNotFoundException;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.SaleService;
import com.sparksupport.nidhin.sales_products.domain.Sale;
import com.sparksupport.nidhin.sales_products.infrastructure.models.ProductEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.models.SaleEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.ProductRepository;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.SaleRepository;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.mapper.SaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Page<Sale> getAllSales(Pageable pageable) {
        Page<SaleEntity> paginatedSales = saleRepository.findAll(pageable);
        List<Sale> sales = paginatedSales.getContent().stream()
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(sales, pageable, paginatedSales.getTotalElements());
    }

    @Override
    public Sale getSaleById(int id) {
        SaleEntity saleEntity = saleRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return SaleMapper.toDomain(saleEntity);
    }

    @Override
    public Sale addSale(SaleDto saleRequest) {
        ProductEntity product = getProduct(saleRequest.getProductId());
        Sale sale = Sale.create(saleRequest);
        SaleEntity saleEntity = SaleMapper.toEntity(sale);
        checkAvailabilityAndUpdateStock(product, sale);
        saleEntity.setProduct(product);
        SaleEntity savedSale = saleRepository.save(saleEntity);
        return SaleMapper.toDomain(savedSale);
    }

    private void checkAvailabilityAndUpdateStock(ProductEntity product, Sale sale) {
        if (product.getQuantity() == 0) {
            throw new InsufficientStockException("The item is out of stock");
        }
        int remainingStock = product.getQuantity() - sale.getQuantity();
        if (remainingStock < 0) {
            throw new InsufficientStockException("Quantity requested exceeds available stock, currently " + product.getQuantity());
        }
        product.setQuantity(remainingStock);
    }

    @Override
    @Transactional
    public Sale updateSale(int saleId, SaleDto saleRequest) {
        // Fetch the existing sale and product
        SaleEntity existingSaleEntity = saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException(saleId));
        Sale existingSale = SaleMapper.toDomain(existingSaleEntity);

        ProductEntity oldProduct = getProduct(existingSale.getProductId());
        ProductEntity newProduct = getProduct(saleRequest.getProductId());

        Sale updatedSale = Sale.create(saleRequest);
        SaleEntity updatedSaleEntity = SaleMapper.toEntity(updatedSale);

        // Update the sale entity
        updatedSaleEntity.setId(saleId);
        updatedSaleEntity.setProduct(newProduct);

        // Check and update stock for old product
        if (oldProduct != newProduct) {
            // Restore stock of the old product
            oldProduct.setQuantity(oldProduct.getQuantity() + existingSaleEntity.getQuantity());
            productRepository.save(oldProduct);

            // Check and update stock for the new product
            checkAvailabilityAndUpdateStock(newProduct, updatedSale);
            saleRepository.save(updatedSaleEntity);
        } else {
            // Check and update stock for the same product
            int stockChange = existingSaleEntity.getQuantity() - updatedSale.getQuantity();
            if (stockChange != 0) {
                oldProduct.setQuantity(oldProduct.getQuantity() + existingSaleEntity.getQuantity());
                checkAvailabilityAndUpdateStock(oldProduct, updatedSale);
                productRepository.save(oldProduct);
            }
        }
        SaleEntity savedSaleEntity = saleRepository.save(updatedSaleEntity);
        return SaleMapper.toDomain(savedSaleEntity);
    }

    private ProductEntity getProduct(int productId) {
        return productRepository.findByIdWithSales(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public boolean deleteSale(int id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return true;
        } else throw new SaleNotFoundException(id);
    }
}
