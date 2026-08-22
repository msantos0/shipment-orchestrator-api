package com.shipmentorchestrator.api.shipment.api;

import java.time.Instant;

import com.shipmentorchestrator.api.shipment.application.ShipmentOutput;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

public record ShipmentResponse(
        String id,
        String origin,
        String destination,
        String trackingCode,
        ShipmentStatus status,
        Instant createdAt) {

    public static ShipmentResponse from(ShipmentOutput output) {
        return new ShipmentResponse(
                output.id(),
                output.origin(),
                output.destination(),
                output.trackingCode(),
                output.status(),
                output.createdAt());
    }
}
