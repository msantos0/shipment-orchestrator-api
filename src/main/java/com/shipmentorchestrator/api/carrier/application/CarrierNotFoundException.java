package com.shipmentorchestrator.api.carrier.application;

public class CarrierNotFoundException extends RuntimeException {

    public CarrierNotFoundException(String id) {
        super("Carrier not found: " + id);
    }
}