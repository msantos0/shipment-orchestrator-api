package com.shipmentorchestrator.api.shipment.application;

import java.time.Instant;

import com.shipmentorchestrator.api.shipment.domain.TrackingEventType;

public record TrackingEventOutput(
        String id,
        String shipmentId,
        TrackingEventType eventType,
        Instant eventDate,
        String location,
        String description) {
}
