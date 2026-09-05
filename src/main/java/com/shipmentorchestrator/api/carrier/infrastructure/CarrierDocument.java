package com.shipmentorchestrator.api.carrier.infrastructure;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carriers")
public class CarrierDocument {

    @Id
    private String id;
    private String name;
    private String cnpj;
    private boolean active;
    private Instant createdAt;
}