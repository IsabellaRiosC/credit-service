package com.nttdata.creditservice.infrastructure.redis;

import com.nttdata.creditservice.domain.port.CreditCachePort;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RedisCreditCacheAdapter implements CreditCachePort {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Override
    public Mono<BigDecimal> getBalance(String creditId) {
        return redisTemplate.opsForValue()
                .get(balanceKey(creditId))
                .map(BigDecimal::new)
                .onErrorReturn(BigDecimal.ZERO);
    }

    @Override
    public Mono<Void> putBalance(String creditId, BigDecimal balance) {
        return redisTemplate.opsForValue()
                .set(balanceKey(creditId), balance.toPlainString(), Duration.ofMinutes(15))
                .then();
    }

    @Override
    public Mono<Void> evictBalance(String creditId) {
        return redisTemplate.delete(balanceKey(creditId)).then();
    }

    @Override
    public Mono<Boolean> registerOperation(String operationId) {
        return redisTemplate.opsForValue()
                .setIfAbsent(operationKey(operationId), "1", Duration.ofHours(1))
                .onErrorReturn(true);
    }

    private String balanceKey(String creditId) {
        return "credit:balance:" + creditId;
    }

    private String operationKey(String operationId) {
        return "credit:operation:" + operationId;
    }
}
