package com.sparksupport.nidhin.sales_products.infrastructure.repositories;

import com.sparksupport.nidhin.sales_products.infrastructure.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findById(int id);

    Page<ProductEntity> findAll(Pageable pageable);

    @Query("SELECT p FROM products p LEFT JOIN FETCH p.sales WHERE p.id = :productId")
    Optional<ProductEntity> findByIdWithSales(@Param("productId") int productId);
}