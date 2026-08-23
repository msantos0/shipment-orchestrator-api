package com.shipmentorchestrator.api.shipment.api;

import com.shipmentorchestrator.api.shipment.application.UpdateShipmentCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateShipmentRequest(
        @NotBlank @Size(max = 120) String origin,
        @NotBlank @Size(max = 120) String destination,
        @NotBlank @Size(max = 80) String trackingCode) {

    public UpdateShipmentCommand toCommand() {
        return new UpdateShipmentCommand(origin, destination, trackingCode);
    }
}
