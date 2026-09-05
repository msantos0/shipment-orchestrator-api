package com.shipmentorchestrator.api.carrier.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shipmentorchestrator.api.carrier.application.CarrierOutput;

public record CarrierPageResponse(
        List<CarrierResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static CarrierPageResponse from(Page<CarrierOutput> output) {
        return new CarrierPageResponse(
                output.getContent().stream().map(CarrierResponse::from).toList(),
                output.getNumber(),
                output.getSize(),
                output.getTotalElements(),
                output.getTotalPages());
    }
}