package com.nttdata.creditservice.domain.port;

import com.nttdata.creditservice.api.dto.CreditEventAvroDto;
import reactor.core.publisher.Mono;

public interface CreditEventPublisherPort {
    Mono<Void> publish(CreditEventAvroDto event);
}
