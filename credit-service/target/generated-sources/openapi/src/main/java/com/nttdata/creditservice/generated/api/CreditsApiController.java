package com.nttdata.creditservice.generated.api;

import com.nttdata.creditservice.generated.model.BalanceOperationRequest;
import com.nttdata.creditservice.generated.model.CreateCreditRequest;
import com.nttdata.creditservice.generated.model.CreditResponse;
import com.nttdata.creditservice.generated.model.UpdateCreditRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-03-27T15:51:37.476696400-05:00[America/Lima]", comments = "Generator version: 7.5.0")
@Controller
@RequestMapping("${openapi.creditService.base-path:}")
public class CreditsApiController implements CreditsApi {

    private final CreditsApiDelegate delegate;

    public CreditsApiController(@Autowired(required = false) CreditsApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CreditsApiDelegate() {});
    }

    @Override
    public CreditsApiDelegate getDelegate() {
        return delegate;
    }

}
