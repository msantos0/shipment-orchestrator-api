package com.shipmentorchestrator.api.carrier.application;

public record UpdateCarrierCommand(String name, boolean active) {
}