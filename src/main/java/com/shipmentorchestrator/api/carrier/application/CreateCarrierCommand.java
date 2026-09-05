package com.shipmentorchestrator.api.carrier.application;

public record CreateCarrierCommand(String name, String cnpj) {
}