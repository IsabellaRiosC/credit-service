package com.nttdata.creditservice.domain.service.impl;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditEventAvroDto;
import com.nttdata.creditservice.api.dto.CreditOperationRequestDto;
import com.nttdata.creditservice.api.dto.CreditResponseDto;
import com.nttdata.creditservice.api.dto.CreditUpdateRequestDto;
import com.nttdata.creditservice.domain.mapper.CreditMapper;
import com.nttdata.creditservice.domain.policy.CreditValidationPolicy;
import com.nttdata.creditservice.domain.port.CreditCachePort;
import com.nttdata.creditservice.domain.port.CreditEventPublisherPort;
import com.nttdata.creditservice.domain.port.CreditRepositoryPort;
import com.nttdata.creditservice.domain.port.CustomerValidationPort;
import com.nttdata.creditservice.domain.service.CreditService;
import com.nttdata.creditservice.generated.api.CreditsApiDelegate;
import com.nttdata.creditservice.generated.model.CreateCreditRequest;
import com.nttdata.creditservice.generated.model.CreditResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService, CreditsApiDelegate {
        // --- Contract-first API implementation ---
        @Override
        public Mono<ResponseEntity<CreditResponse>> createCredit(Mono<CreateCreditRequest> createCreditRequest,
                        ServerWebExchange exchange) {
                // Obtener usuario autenticado (ejemplo: header X-Auth-User)
                String requestedBy = exchange.getRequest().getHeaders().getFirst("X-Auth-User");
                if (requestedBy == null || requestedBy.isBlank()) {
                        return Mono.just(ResponseEntity.status(401).build());
                }
                return createCreditRequest
                                .map(req -> {
                                        CreditCreateRequestDto dto = new CreditCreateRequestDto();
                                        dto.setCustomerId(req.getCustomerId());
                                        dto.setAmount(req.getAmount() != null
                                                        ? java.math.BigDecimal.valueOf(req.getAmount())
                                                        : java.math.BigDecimal.ZERO);
                                        return dto;
                                })
                                .flatMap(dto -> this.create(dto, requestedBy))
                                .map(this::toGenerated)
                                .map(body -> ResponseEntity.status(201).body(body));
        }

        private CreditResponse toGenerated(CreditResponseDto dto) {
                CreditResponse gen = new CreditResponse();
                gen.setCreditId(dto.getCreditId());
                gen.setCustomerId(dto.getCustomerId());
                gen.setAmount(dto.getAmount() != null ? dto.getAmount().doubleValue() : null);
                gen.setBalance(dto.getBalance() != null ? dto.getBalance().doubleValue() : null);
                if (dto.getCreatedAt() != null) {
                        gen.setCreatedAt(java.time.OffsetDateTime.ofInstant(dto.getCreatedAt(),
                                        java.time.ZoneOffset.UTC));
                }
                // Completar otros campos si es necesario
                return gen;
        }

        private final CreditValidationPolicy creditValidationPolicy;
        private final CreditRepositoryPort creditRepositoryPort;
        private final CustomerValidationPort customerValidationPort;
        private final CreditEventPublisherPort creditEventPublisherPort;
        private final CreditCachePort creditCachePort;

        @Override
        public Mono<CreditResponseDto> create(CreditCreateRequestDto request, String requestedBy) {
                creditValidationPolicy.validateRequestedBy(requestedBy);
                creditValidationPolicy.validateCreate(request);

                return customerValidationPort.existsCustomer(request.getCustomerId(), requestedBy)
                                .publishOn(Schedulers.parallel())
                                .doOnNext(exists -> creditValidationPolicy.validateCustomerExists(exists,
                                                request.getCustomerId()))
                                .flatMap(valid -> creditRepositoryPort.findById(requestedBy)) // Adaptar si hay campo
                                                                                              // único
                                .flatMap(existing -> Mono.<CreditResponseDto>error(
                                                new ResponseStatusException(HttpStatus.CONFLICT,
                                                                "Credit already exists")))
                                .switchIfEmpty(Mono.defer(() -> creditRepositoryPort
                                                .save(CreditMapper.toNewRecord(request, requestedBy))
                                                .flatMap(saved -> creditCachePort
                                                                .putBalance(saved.creditId(), saved.balance())
                                                                .then(creditEventPublisherPort
                                                                                .publish(CreditEventAvroDto.builder()
                                                                                                .eventType("CREDIT_CREATED")
                                                                                                .creditId(saved.creditId())
                                                                                                .customerId(saved
                                                                                                                .customerId())
                                                                                                .balance(saved.balance())
                                                                                                .build()))
                                                                .thenReturn(saved))
                                                .map(CreditMapper::toDto)))
                                .cast(CreditResponseDto.class)
                                .subscribeOn(Schedulers.boundedElastic())
                                .doOnError(ex -> log.error("Error creating credit", ex))
                                .onErrorResume(DuplicateKeyException.class,
                                                ex -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT,
                                                                "Credit already exists")));
        }

        @Override
        public Mono<CreditResponseDto> findById(String creditId) {
                creditValidationPolicy.validateCreditId(creditId);
                return creditRepositoryPort.findById(creditId)
                                .switchIfEmpty(Mono.error(
                                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found")))
                                .publishOn(Schedulers.parallel())
                                .map(CreditMapper::toDto);
        }

        @Override
        public Flux<CreditResponseDto> findAll(String customerId) {
                return creditRepositoryPort.findByCustomerId(customerId)
                                .publishOn(Schedulers.parallel())
                                .map(CreditMapper::toDto);
        }

        @Override
        public Mono<CreditResponseDto> update(String creditId, CreditUpdateRequestDto request, String requestedBy) {
                creditValidationPolicy.validateCreditId(creditId);
                creditValidationPolicy.validateUpdate(request);
                return creditRepositoryPort.findById(creditId)
                                .switchIfEmpty(Mono.error(
                                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found")))
                                .flatMap(existing -> creditRepositoryPort
                                                .save(CreditMapper.toUpdatedRecord(existing, request, requestedBy)))
                                .map(CreditMapper::toDto);
        }

        @Override
        public Mono<Void> delete(String creditId, String requestedBy) {
                creditValidationPolicy.validateCreditId(creditId);
                return creditRepositoryPort.deleteById(creditId)
                                .then(creditCachePort.evictBalance(creditId));
        }

        @Override
        public Mono<CreditResponseDto> operate(String creditId, CreditOperationRequestDto request, String requestedBy) {
                creditValidationPolicy.validateCreditId(creditId);
                creditValidationPolicy.validateOperation(request);
                // Lógica de operación de balance (ejemplo)
                return creditRepositoryPort.findById(creditId)
                                .switchIfEmpty(Mono.error(
                                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found")))
                                .flatMap(existing -> {
                                        // Aquí se aplicaría la lógica de operación sobre el balance
                                        // ...
                                        return creditRepositoryPort.save(existing)
                                                        .map(CreditMapper::toDto);
                                });
        }

        @Override
        public Mono<ResponseEntity<CreditResponse>> getCreditById(String creditId, ServerWebExchange exchange) {
                return findById(creditId)
                                .map(this::toGenerated)
                                .map(ResponseEntity::ok)
                                .onErrorResume(ResponseStatusException.class, ex ->
                                                Mono.just(ResponseEntity.status(ex.getStatusCode()).build()));
        }

        @Override
        public Mono<ResponseEntity<Flux<CreditResponse>>> getCredits(String customerId, ServerWebExchange exchange) {
                Flux<CreditResponse> responseFlux = findAll(customerId)
                                .map(this::toGenerated);
                return Mono.just(ResponseEntity.ok(responseFlux));
        }
}
