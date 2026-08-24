package com.shipmentorchestrator.api.shipment.domain;

import java.time.Instant;

public record ShipmentEvent(
        String shipmentId,
        ShipmentStatus status,
        Instant occurredAt) {
}
