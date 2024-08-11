package com.sparksupport.nidhin.sales_products.infrastructure.repositories;

import com.sparksupport.nidhin.sales_products.infrastructure.models.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Integer> {
    Optional<SaleEntity> findById(int id);
}
