package com.nttdata.creditservice.generated.api;

import com.nttdata.creditservice.generated.model.BalanceOperationRequest;
import com.nttdata.creditservice.generated.model.CreateCreditRequest;
import com.nttdata.creditservice.generated.model.CreditResponse;
import com.nttdata.creditservice.generated.model.UpdateCreditRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.Part;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link CreditsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-03-27T15:52:46.291209300-05:00[America/Lima]", comments = "Generator version: 7.5.0")
public interface CreditsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/credits : Create credit
     *
     * @param createCreditRequest  (required)
     * @return Credit created (status code 201)
     * @see CreditsApi#createCredit
     */
    default Mono<ResponseEntity<CreditResponse>> createCredit(Mono<CreateCreditRequest> createCreditRequest,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(createCreditRequest).then(Mono.empty());

    }

    /**
     * DELETE /api/credits/{creditId} : Delete credit
     *
     * @param creditId  (required)
     * @return Credit deleted (status code 204)
     * @see CreditsApi#deleteCredit
     */
    default Mono<ResponseEntity<Void>> deleteCredit(String creditId,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        return result.then(Mono.empty());

    }

    /**
     * GET /api/credits/{creditId} : Get credit by id
     *
     * @param creditId  (required)
     * @return Credit found (status code 200)
     * @see CreditsApi#getCreditById
     */
    default Mono<ResponseEntity<CreditResponse>> getCreditById(String creditId,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * GET /api/credits : List credits
     *
     * @param customerId  (optional)
     * @return Credit list (status code 200)
     * @see CreditsApi#getCredits
     */
    default Mono<ResponseEntity<Flux<CreditResponse>>> getCredits(String customerId,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" } ]";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * POST /api/credits/{creditId}/operations : Apply balance operation
     *
     * @param creditId  (required)
     * @param balanceOperationRequest  (required)
     * @return Operation applied (status code 200)
     * @see CreditsApi#operateBalance
     */
    default Mono<ResponseEntity<CreditResponse>> operateBalance(String creditId,
        Mono<BalanceOperationRequest> balanceOperationRequest,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(balanceOperationRequest).then(Mono.empty());

    }

    /**
     * PUT /api/credits/{creditId} : Update credit
     *
     * @param creditId  (required)
     * @param updateCreditRequest  (required)
     * @return Credit updated (status code 200)
     * @see CreditsApi#updateCredit
     */
    default Mono<ResponseEntity<CreditResponse>> updateCredit(String creditId,
        Mono<UpdateCreditRequest> updateCreditRequest,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"creditId\" : \"creditId\", \"amount\" : 0.8008281904610115, \"balance\" : 6.027456183070403, \"customerId\" : \"customerId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(updateCreditRequest).then(Mono.empty());

    }

}
