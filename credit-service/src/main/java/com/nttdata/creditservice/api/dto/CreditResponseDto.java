package com.nttdata.creditservice.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class CreditResponseDto {
    private String creditId;
    private String customerId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String status;
    private String createdBy;
    private Instant createdAt;
    private Instant updatedAt;
}
