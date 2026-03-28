package com.nttdata.creditservice.domain.service.impl;

import com.nttdata.creditservice.api.dto.CreditCreateRequestDto;
import com.nttdata.creditservice.api.dto.CreditResponseDto;
import com.nttdata.creditservice.domain.mapper.CreditMapper;
import com.nttdata.creditservice.domain.policy.CreditValidationPolicy;
import com.nttdata.creditservice.domain.port.CreditCachePort;
import com.nttdata.creditservice.domain.port.CreditEventPublisherPort;
import com.nttdata.creditservice.domain.port.CreditRepositoryPort;
import com.nttdata.creditservice.domain.port.CustomerValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreditServiceImplTest {
    @Mock
    private CreditValidationPolicy creditValidationPolicy;
    @Mock
    private CreditRepositoryPort creditRepositoryPort;
    @Mock
    private CustomerValidationPort customerValidationPort;
    @Mock
    private CreditEventPublisherPort creditEventPublisherPort;
    @Mock
    private CreditCachePort creditCachePort;
    @InjectMocks
    private CreditServiceImpl creditServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createShouldPersistCredit() {
        CreditCreateRequestDto req = new CreditCreateRequestDto();
        req.setCustomerId("CUS-1");
        req.setAmount(BigDecimal.valueOf(1000));
        when(customerValidationPort.existsCustomer(any(), any())).thenReturn(Mono.just(true));
        when(creditRepositoryPort.findById(any())).thenReturn(Mono.empty());
        when(creditRepositoryPort.save(any())).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        when(creditCachePort.putBalance(any(), any())).thenReturn(Mono.empty());
        when(creditEventPublisherPort.publish(any())).thenReturn(Mono.empty());

        StepVerifier.create(creditServiceImpl.create(req, "demo.user"))
                .expectNextMatches(dto -> dto.getCustomerId().equals("CUS-1") && dto.getAmount().intValue() == 1000)
                .verifyComplete();
    }
}
