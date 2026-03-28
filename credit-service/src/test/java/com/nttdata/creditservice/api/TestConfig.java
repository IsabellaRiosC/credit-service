package com.nttdata.creditservice.api;

import com.nttdata.creditservice.domain.port.CustomerValidationPort;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@TestConfiguration
public class TestConfig {
    @Bean
    public CustomerValidationPort customerValidationPort() {
        CustomerValidationPort mock = Mockito.mock(CustomerValidationPort.class);
        Mockito.when(mock.existsCustomer(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(true));
        return mock;
    }

    @Bean
    public com.nttdata.creditservice.domain.port.CreditRepositoryPort creditRepositoryPort() {
        com.nttdata.creditservice.domain.port.CreditRepositoryPort mock = Mockito.mock(com.nttdata.creditservice.domain.port.CreditRepositoryPort.class);
        Mockito.when(mock.findById(Mockito.anyString())).thenReturn(Mono.empty());
        Mockito.when(mock.save(Mockito.any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        Mockito.when(mock.findByCustomerId(Mockito.anyString())).thenReturn(reactor.core.publisher.Flux.empty());
        Mockito.when(mock.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
        return mock;
    }

    @Bean
    public com.nttdata.creditservice.domain.port.CreditCachePort creditCachePort() {
        com.nttdata.creditservice.domain.port.CreditCachePort mock = Mockito.mock(com.nttdata.creditservice.domain.port.CreditCachePort.class);
        Mockito.when(mock.putBalance(Mockito.anyString(), Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(mock.evictBalance(Mockito.anyString())).thenReturn(Mono.empty());
        return mock;
    }

    @Bean
    public com.nttdata.creditservice.domain.port.CreditEventPublisherPort creditEventPublisherPort() {
        com.nttdata.creditservice.domain.port.CreditEventPublisherPort mock = Mockito.mock(com.nttdata.creditservice.domain.port.CreditEventPublisherPort.class);
        Mockito.when(mock.publish(Mockito.any())).thenReturn(Mono.empty());
        return mock;
    }
}
