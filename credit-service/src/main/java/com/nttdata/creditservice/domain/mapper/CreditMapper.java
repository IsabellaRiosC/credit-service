package com.nttdata.creditservice.domain.mapper;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditOperationRequestDto;
import com.nttdata.creditservice.api.dto.CreditResponseDto;
import com.nttdata.creditservice.api.dto.CreditUpdateRequestDto;
import com.nttdata.creditservice.domain.port.CreditRecord;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class CreditMapper {

    private CreditMapper() {
    }

    public static CreditRecord toNewRecord(CreditCreateRequestDto request, String requestedBy) {
        Instant now = Instant.now();
        BigDecimal amount = request.getAmount() == null ? BigDecimal.ZERO : request.getAmount();
        return new CreditRecord(
                "CRED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                request.getCustomerId(),
                amount,
                amount, // balance inicial igual al monto
                "ACTIVE",
                requestedBy,
                now,
                now);
    }

    public static CreditRecord toUpdatedRecord(CreditRecord current, CreditUpdateRequestDto request,
            String requestedBy) {
        return new CreditRecord(
                current.creditId(),
                current.customerId(),
                current.amount(),
                current.balance(),
                request.getStatus(),
                requestedBy,
                current.createdAt(),
                Instant.now());
    }

    public static CreditRecord applyOperation(CreditRecord current, CreditOperationRequestDto request) {
        BigDecimal amount = request.getAmount() == null ? BigDecimal.ZERO : request.getAmount();
        BigDecimal nextBalance = "DEBIT".equalsIgnoreCase(request.getOperationType())
                ? current.balance().subtract(amount)
                : current.balance().add(amount);
        return new CreditRecord(
                current.creditId(),
                current.customerId(),
                current.amount(),
                nextBalance,
                current.status(),
                current.createdBy(),
                current.createdAt(),
                Instant.now());
    }

    public static CreditResponseDto toDto(CreditRecord record) {
        CreditResponseDto dto = new CreditResponseDto();
        dto.setCreditId(record.creditId());
        dto.setCustomerId(record.customerId());
        dto.setAmount(record.amount());
        dto.setBalance(record.balance());
        dto.setStatus(record.status());
        dto.setCreatedBy(record.createdBy());
        dto.setCreatedAt(record.createdAt());
        dto.setUpdatedAt(record.updatedAt());
        return dto;
    }
}
