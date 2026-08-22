package com.shipmentorchestrator.api.shipment.application;

import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

public record UpdateShipmentCommand(
        String origin,
        String destination,
        String trackingCode,
        ShipmentStatus status) {
}
