package com.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto {
    private Long id;
    private LocalDate date;
    private BigDecimal discount;
    private Integer quantity;
    private CustomerDto customerDto;
    private PaymentDto paymentDto;
    private ProductDto productDto;
}
