package com.shipmentorchestrator.api.shipment.domain;

public enum TrackingEventType {
    CREATED,
    PLANNED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}