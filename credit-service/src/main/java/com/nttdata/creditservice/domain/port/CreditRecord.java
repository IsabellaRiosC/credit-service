package com.nttdata.creditservice.domain.port;

import java.math.BigDecimal;
import java.time.Instant;

public record CreditRecord(
        String creditId,
        String customerId,
        BigDecimal amount,
        BigDecimal balance,
        String status,
        String createdBy,
        Instant createdAt,
        Instant updatedAt) {
}
