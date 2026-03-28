package com.nttdata.creditservice.api.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreditCacheDto {
    String creditId;
    BigDecimal balance;
}
