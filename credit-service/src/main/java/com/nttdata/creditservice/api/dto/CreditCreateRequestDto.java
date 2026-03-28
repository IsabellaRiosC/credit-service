package com.nttdata.creditservice.api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreditCreateRequestDto {
    private String customerId;
    private BigDecimal amount;
}
