package com.nttdata.creditservice.domain.port;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditOperationRequestDto;
import com.nttdata.creditservice.api.dto.CreditResponseDto;
import com.nttdata.creditservice.api.dto.CreditUpdateRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditUseCase {
    Mono<CreditResponseDto> create(CreditCreateRequestDto request, String requestedBy);

    Mono<CreditResponseDto> findById(String creditId);

    Flux<CreditResponseDto> findAll(String customerId);

    Mono<CreditResponseDto> update(String creditId, CreditUpdateRequestDto request, String requestedBy);

    Mono<Void> delete(String creditId, String requestedBy);

    Mono<CreditResponseDto> operate(String creditId, CreditOperationRequestDto request, String requestedBy);
}
