package com.nttdata.creditservice.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditMongoRepository extends ReactiveMongoRepository<CreditDocument, String> {
    Flux<CreditDocument> findByCustomerId(String customerId);
}
