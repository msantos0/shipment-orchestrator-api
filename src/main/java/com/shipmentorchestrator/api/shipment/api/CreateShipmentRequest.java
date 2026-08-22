package com.shipmentorchestrator.api.shipment.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.shipmentorchestrator.api.shipment.application.CreateShipmentCommand;

public record CreateShipmentRequest(
        @NotBlank @Size(max = 120) String origin,
    @NotBlank @Size(max = 120) String destination,
    @NotBlank @Size(max = 80) String trackingCode) {

    public CreateShipmentCommand toCommand() {
        return new CreateShipmentCommand(origin, destination, trackingCode);
    }
}
