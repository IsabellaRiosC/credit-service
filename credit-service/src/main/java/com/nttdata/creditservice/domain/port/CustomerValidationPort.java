package com.nttdata.creditservice.domain.port;

import reactor.core.publisher.Mono;

public interface CustomerValidationPort {
    Mono<Boolean> existsCustomer(String customerId, String requestedBy);
}
