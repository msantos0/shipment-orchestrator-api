package com.shipmentorchestrator.api.carrier.api;

import com.shipmentorchestrator.api.carrier.application.CreateCarrierCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCarrierRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 18) String cnpj) {

    public CreateCarrierCommand toCommand() {
        return new CreateCarrierCommand(name, cnpj);
    }
}