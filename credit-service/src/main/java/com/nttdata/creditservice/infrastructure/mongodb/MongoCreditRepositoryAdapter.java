package com.nttdata.creditservice.infrastructure.mongodb;

import com.nttdata.creditservice.domain.port.CreditRecord;
import com.nttdata.creditservice.domain.port.CreditRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoCreditRepositoryAdapter implements CreditRepositoryPort {
    private final CreditMongoRepository creditMongoRepository;

    @Override
    public Mono<CreditRecord> save(CreditRecord creditRecord) {
        return creditMongoRepository.save(toDocument(creditRecord)).map(this::toRecord);
    }

    @Override
    public Mono<CreditRecord> findById(String creditId) {
        return creditMongoRepository.findById(creditId).map(this::toRecord);
    }

    @Override
    public Flux<CreditRecord> findAll() {
        return creditMongoRepository.findAll().map(this::toRecord);
    }

    @Override
    public Flux<CreditRecord> findByCustomerId(String customerId) {
        return creditMongoRepository.findByCustomerId(customerId).map(this::toRecord);
    }

    @Override
    public Mono<Void> deleteById(String creditId) {
        return creditMongoRepository.deleteById(creditId);
    }

    private CreditDocument toDocument(CreditRecord record) {
        return CreditDocument.builder()
                .creditId(record.creditId())
                .customerId(record.customerId())
                .amount(record.amount())
                .balance(record.balance())
                .status(record.status())
                .createdBy(record.createdBy())
                .createdAt(record.createdAt())
                .updatedAt(record.updatedAt())
                .build();
    }

    private CreditRecord toRecord(CreditDocument document) {
        return new CreditRecord(
                document.getCreditId(),
                document.getCustomerId(),
                document.getAmount(),
                document.getBalance(),
                document.getStatus(),
                document.getCreatedBy(),
                document.getCreatedAt(),
                document.getUpdatedAt());
    }
}
