package com.sparksupport.nidhin.sales_products.infrastructure.models;

import com.sparksupport.nidhin.sales_products.application.utilities.RoleName;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
