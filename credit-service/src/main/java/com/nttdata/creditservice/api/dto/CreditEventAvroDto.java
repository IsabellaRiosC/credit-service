package com.nttdata.creditservice.api.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreditEventAvroDto {
    String eventType;
    String creditId;
    String customerId;
    BigDecimal balance;
    String operationId;
}
