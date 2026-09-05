package com.shipmentorchestrator.api.carrier.application;

import java.time.Instant;

public record CarrierOutput(
        String id,
        String name,
        String cnpj,
        boolean active,
        Instant createdAt) {
}