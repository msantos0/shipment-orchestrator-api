package com.shipmentorchestrator.api.shipment.application;

import java.time.Instant;

import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

public record ShipmentOutput(
        String id,
        String origin,
        String destination,
        ShipmentStatus status,
        Instant createdAt) {
}
