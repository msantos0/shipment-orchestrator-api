package com.shipmentorchestrator.api.shipment.application;

public class ShipmentNotFoundException extends RuntimeException {

    public ShipmentNotFoundException(String id) {
        super("Shipment not found: " + id);
    }
}
