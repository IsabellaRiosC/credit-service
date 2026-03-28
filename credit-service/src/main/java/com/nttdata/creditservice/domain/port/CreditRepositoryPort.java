package com.nttdata.creditservice.domain.port;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepositoryPort {
    Mono<CreditRecord> save(CreditRecord creditRecord);

    Mono<CreditRecord> findById(String creditId);

    Flux<CreditRecord> findAll();

    Flux<CreditRecord> findByCustomerId(String customerId);

    Mono<Void> deleteById(String creditId);
}
