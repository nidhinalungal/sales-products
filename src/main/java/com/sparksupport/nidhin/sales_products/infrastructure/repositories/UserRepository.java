package com.sparksupport.nidhin.sales_products.infrastructure.repositories;


import com.sparksupport.nidhin.sales_products.infrastructure.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity>  findByEmail(String username);

    Optional<UserEntity> findByUserId(UUID userId);

}
