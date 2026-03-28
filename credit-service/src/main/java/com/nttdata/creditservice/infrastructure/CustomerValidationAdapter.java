package com.nttdata.creditservice.infrastructure;

import com.nttdata.creditservice.domain.port.CustomerValidationPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerValidationAdapter implements CustomerValidationPort {
    @Override
    public Mono<Boolean> existsCustomer(String customerId, String requestedBy) {
        // Implementación temporal: siempre retorna true
        return Mono.just(true);
    }
}

