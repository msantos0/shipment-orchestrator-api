package com.shipmentorchestrator.api.shipment.application;

public record CreateShipmentCommand(
        String origin,
        String destination) {
}
