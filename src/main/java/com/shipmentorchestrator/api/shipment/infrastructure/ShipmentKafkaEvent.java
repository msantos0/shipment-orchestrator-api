package com.shipmentorchestrator.api.shipment.infrastructure;

import java.time.Instant;

public record ShipmentKafkaEvent(
        String shipmentId,
        String eventType,
        Instant occurredAt) {
}
