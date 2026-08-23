package com.shipmentorchestrator.api.shipment.application;

public record UpdateShipmentCommand(
        String origin,
        String destination,
        String trackingCode) {
}
