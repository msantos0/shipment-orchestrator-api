package com.shipmentorchestrator.api.shipment.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shipmentorchestrator.api.shipment.application.ShipmentOutput;

public record ShipmentPageResponse(
        List<ShipmentResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static ShipmentPageResponse from(Page<ShipmentOutput> output) {
        return new ShipmentPageResponse(
                output.getContent().stream().map(ShipmentResponse::from).toList(),
                output.getNumber(),
                output.getSize(),
                output.getTotalElements(),
                output.getTotalPages());
    }
}
