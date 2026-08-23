package com.shipmentorchestrator.api.shipment.api;

import java.time.Instant;

import com.shipmentorchestrator.api.shipment.application.TrackingEventOutput;
import com.shipmentorchestrator.api.shipment.domain.TrackingEventType;

public record TrackingEventResponse(
        String id,
        String shipmentId,
        TrackingEventType eventType,
        Instant eventDate,
        String location,
        String description) {

    public static TrackingEventResponse from(TrackingEventOutput output) {
        return new TrackingEventResponse(
                output.id(),
                output.shipmentId(),
                output.eventType(),
                output.eventDate(),
                output.location(),
                output.description());
    }
}
