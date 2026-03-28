package com.nttdata.creditservice.domain.policy;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditOperationRequestDto;
import com.nttdata.creditservice.api.dto.CreditUpdateRequestDto;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CreditValidationPolicy {

    public void validateRequestedBy(String requestedBy) {
        if (requestedBy == null || requestedBy.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authenticated user");
        }
    }

    public void validateCreate(CreditCreateRequestDto request) {
        if (request == null || isBlank(request.getCustomerId()) || request.getAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid create credit payload");
        }
    }

    public void validateUpdate(CreditUpdateRequestDto request) {
        if (request == null || isBlank(request.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid update credit payload");
        }
    }

    public void validateOperation(CreditOperationRequestDto request) {
        if (request == null
                || isBlank(request.getOperationType())
                || isBlank(request.getOperationId())
                || request.getAmount() == null
                || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operation payload");
        }
    }

    public void validateCreditId(String creditId) {
        if (isBlank(creditId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credit id");
        }
    }

    public void validateCustomerExists(boolean customerExists, String customerId) {
        if (!customerExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found: " + customerId);
        }
    }

    public void validateSufficientBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
