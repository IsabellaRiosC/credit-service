package com.nttdata.creditservice.infrastructure.mongodb;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "credits")
public class CreditDocument {
    @Id
    private String creditId;
    @Indexed
    private String customerId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String status;
    private String createdBy;
    private Instant createdAt;
    private Instant updatedAt;
}
