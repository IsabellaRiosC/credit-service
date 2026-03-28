package com.nttdata.creditservice.api;

import com.nttdata.creditservice.generated.model.CreateCreditRequest;
import com.nttdata.creditservice.generated.model.CreditResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestConfig.class)
class CreditApiIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createCreditShouldReturn201() {
        CreateCreditRequest req = new CreateCreditRequest();
        req.setCustomerId("CUS-1");
        req.setAmount(1000.0);
        webTestClient.post()
                .uri("/api/credits")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("X-Auth-User", "demo.user")
                .body(Mono.just(req), CreateCreditRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreditResponse.class)
                .value(resp -> {
                    assert resp.getCustomerId().equals("CUS-1");
                    assert resp.getAmount() == 1000.0;
                });
    }
}
