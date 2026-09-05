package com.shipmentorchestrator.api.carrier.api;

import com.shipmentorchestrator.api.carrier.application.UpdateCarrierCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCarrierRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull Boolean active) {

    public UpdateCarrierCommand toCommand() {
        return new UpdateCarrierCommand(name, active);
    }
}