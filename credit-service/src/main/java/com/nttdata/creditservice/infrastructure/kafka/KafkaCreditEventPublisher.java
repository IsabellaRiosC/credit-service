package com.nttdata.creditservice.infrastructure.kafka;

import com.nttdata.creditservice.api.dto.CreditEventAvroDto;
import com.nttdata.creditservice.domain.port.CreditEventPublisherPort;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KafkaCreditEventPublisher implements CreditEventPublisherPort {

    private final KafkaTemplate<String, CreditEventAvroDto> kafkaTemplate;

    public KafkaCreditEventPublisher(
            @Qualifier("creditEventKafkaTemplate") KafkaTemplate<String, CreditEventAvroDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${credit.kafka.topic.credit-created:credit-created-events}")
    private String createdTopic;

    @Value("${credit.kafka.topic.credit-updated:credit-updated-events}")
    private String updatedTopic;

    @Value("${credit.kafka.topic.credit-operation:credit-operation-events}")
    private String operationTopic;

    @Override
    public Mono<Void> publish(CreditEventAvroDto event) {
        String topic = resolveTopic(event.getEventType());
        return Mono.fromFuture(send(topic, event.getCreditId(), event))
                .doOnNext(result -> log.info("Kafka event published topic={} creditId={} offset={}",
                        topic,
                        event.getCreditId(),
                        result.getRecordMetadata().offset()))
                .doOnError(ex -> log.error("Kafka publish error topic={} creditId={}", topic, event.getCreditId(), ex))
                .onErrorResume(ex -> Mono.empty())
                .then();
    }

    private CompletableFuture<SendResult<String, CreditEventAvroDto>> send(
            String topic,
            String key,
            CreditEventAvroDto payload) {
        return kafkaTemplate.send(topic, key, payload);
    }

    private String resolveTopic(String eventType) {
        if ("CREDIT_CREATED".equals(eventType)) {
            return createdTopic;
        }
        if ("CREDIT_UPDATED".equals(eventType)) {
            return updatedTopic;
        }
        return operationTopic;
    }
}
