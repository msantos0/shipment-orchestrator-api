package com.shipmentorchestrator.api.shipment.domain;

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
@Document(collection = "shipments")
public class Shipment {

    @Id
    private String id;
    private String origin;
    private String destination;
    private ShipmentStatus status;
    private Instant createdAt;
}
