package com.shipmentorchestrator.api.carrier.api;

import java.time.Instant;

import com.shipmentorchestrator.api.carrier.application.CarrierOutput;

public record CarrierResponse(
        String id,
        String name,
        String cnpj,
        boolean active,
        Instant createdAt) {

    public static CarrierResponse from(CarrierOutput output) {
        return new CarrierResponse(
                output.id(),
                output.name(),
                output.cnpj(),
                output.active(),
                output.createdAt());
    }
}