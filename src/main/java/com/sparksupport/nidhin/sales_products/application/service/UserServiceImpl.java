package com.sparksupport.nidhin.sales_products.application.service;

import com.sparksupport.nidhin.sales_products.application.controllers.dto.UserRegistrationDto;
import com.sparksupport.nidhin.sales_products.application.service.exceptions.UserAlreadyExistsException;
import com.sparksupport.nidhin.sales_products.application.service.interfaces.UserService;
import com.sparksupport.nidhin.sales_products.infrastructure.models.RoleEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.models.UserEntity;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.RoleRepository;
import com.sparksupport.nidhin.sales_products.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationDto registerUser(UserRegistrationDto registrationDto) {
        List<RoleEntity> roles = roleRepository.findByRoleNameIn(registrationDto.getRoleNames());
        if (userService.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + registrationDto.getEmail() + " already exists.");
        }
        UserEntity user = new UserEntity();
        user.setEmail(registrationDto.getEmail());
        user.setFullName(registrationDto.getFullName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setUserRoles(new HashSet<>(roles));
        UserEntity savedUser = userService.save(user);
        return toUserRegistrationDto(savedUser);
    }

    private UserRegistrationDto toUserRegistrationDto(UserEntity savedUser) {
        return UserRegistrationDto.builder().email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .roleNames(savedUser.getUserRoles().stream().map(RoleEntity::getRoleName).collect(Collectors.toList())).build();
    }
}
