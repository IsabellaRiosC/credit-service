package com.nttdata.creditservice.domain.port;

import java.math.BigDecimal;
import reactor.core.publisher.Mono;

public interface CreditCachePort {
    Mono<BigDecimal> getBalance(String creditId);

    Mono<Void> putBalance(String creditId, BigDecimal balance);

    Mono<Void> evictBalance(String creditId);

    Mono<Boolean> registerOperation(String operationId);
}
