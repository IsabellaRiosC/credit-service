package com.nttdata.creditservice.infrastructure.decorator;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditOperationRequestDto;
import com.nttdata.creditservice.api.dto.CreditResponseDto;
import com.nttdata.creditservice.api.dto.CreditUpdateRequestDto;
import com.nttdata.creditservice.domain.port.CreditUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class LoggingCreditUseCaseDecorator implements CreditUseCase {

    private final CreditUseCase delegate;

    @Override
    public Mono<CreditResponseDto> create(CreditCreateRequestDto request, String requestedBy) {
        log.info("[CreditUseCase] create by={} amount={}", requestedBy, request.getAmount());
        return delegate.create(request, requestedBy);
    }

    @Override
    public Mono<CreditResponseDto> findById(String creditId) {
        log.info("[CreditUseCase] findById creditId={}", creditId);
        return delegate.findById(creditId);
    }

    @Override
    public Flux<CreditResponseDto> findAll(String customerId) {
        log.info("[CreditUseCase] findAll customerId={}", customerId);
        return delegate.findAll(customerId);
    }

    @Override
    public Mono<CreditResponseDto> update(String creditId, CreditUpdateRequestDto request, String requestedBy) {
        log.info("[CreditUseCase] update creditId={} by={}", creditId, requestedBy);
        return delegate.update(creditId, request, requestedBy);
    }

    @Override
    public Mono<Void> delete(String creditId, String requestedBy) {
        log.info("[CreditUseCase] delete creditId={} by={}", creditId, requestedBy);
        return delegate.delete(creditId, requestedBy);
    }

    @Override
    public Mono<CreditResponseDto> operate(String creditId, CreditOperationRequestDto request, String requestedBy) {
        log.info("[CreditUseCase] operate creditId={} opType={} opId={} by={}",
                creditId, request.getOperationType(), request.getOperationId(), requestedBy);
        return delegate.operate(creditId, request, requestedBy);
    }
}
