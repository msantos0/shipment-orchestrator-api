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
    private String trackingCode;
    private ShipmentStatus status;
    private Instant createdAt;

    public static Shipment create(String origin, String destination, String trackingCode) {
        return Shipment.builder()
                .origin(origin)
                .destination(destination)
                .trackingCode(trackingCode)
                .status(ShipmentStatus.PLANNED)
                .createdAt(Instant.now())
                .build();
    }

    public void update(String origin, String destination, String trackingCode, ShipmentStatus status) {
        this.origin = origin;
        this.destination = destination;
        this.trackingCode = trackingCode;
        this.status = status;
    }
}
