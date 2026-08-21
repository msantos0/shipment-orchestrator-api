package com.shipmentorchestrator.api.shipment.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    private String id;
    private String origin;
    private String destination;
    private ShipmentStatus status;
    private Instant createdAt;

    public static Shipment create(String origin, String destination) {
        return Shipment.builder()
                .origin(origin)
                .destination(destination)
                .status(ShipmentStatus.CREATED)
                .createdAt(Instant.now())
                .build();
    }
}
