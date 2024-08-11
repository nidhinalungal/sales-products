package com.sparksupport.nidhin.sales_products.application.config;

import com.sparksupport.nidhin.sales_products.application.utilities.RoleName;
import com.sparksupport.nidhin.sales_products.infrastructure.models.RoleEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.models.UserEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.RoleRepository;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;

@Service
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            insertDefaultRoles();
        }
        if (userRepository.count() == 0) {
            insertSuperAdmin();
        }
    }

    private void insertDefaultRoles() {
        List<RoleEntity> defaultRoles = List.of(
                RoleEntity.builder().roleName(RoleName.ADMIN).build(),
                RoleEntity.builder().roleName(RoleName.USER).build(),
                RoleEntity.builder().roleName(RoleName.GUEST).build()
        );
        roleRepository.saveAll(defaultRoles);
    }

    private void insertSuperAdmin() {
        List<UserEntity> superAdmin = List.of(
                UserEntity.builder().email("superadmin@gmail.com")
                        .password("$2a$10$UBcceKAVwuCsJAJNP/O3jOZ2oQhFXTa4cEif6SODOohCcIXgWJkK2")
                        .fullName("Super Admin").userRoles(new HashSet<>(roleRepository.findAll())).build()
        );
        userRepository.saveAll(superAdmin);
    }
}
