package com.sparksupport.nidhin.sales_products.application.controllers.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Integer code;
    private String message;
}