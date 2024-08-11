package com.sparksupport.nidhin.sales_products.infrastructure.repositories;

import com.sparksupport.nidhin.sales_products.application.utilities.RoleName;
import com.sparksupport.nidhin.sales_products.infrastructure.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByRoleName(String username);

    List<RoleEntity> findByRoleNameIn(List<RoleName> roleNames);

}

