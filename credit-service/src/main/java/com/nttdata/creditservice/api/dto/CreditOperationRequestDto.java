package com.nttdata.creditservice.api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreditOperationRequestDto {
    private BigDecimal amount;
    private String operationType;
    private String operationId;
}
