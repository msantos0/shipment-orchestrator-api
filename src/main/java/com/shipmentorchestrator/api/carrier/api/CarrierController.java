package com.shipmentorchestrator.api.carrier.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipmentorchestrator.api.carrier.application.CarrierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
@Tag(name = "Carriers", description = "Carrier management operations")
public class CarrierController {

    private final CarrierService carrierService;

    @PostMapping
    @Operation(summary = "Create a carrier", description = "Creates an active carrier")
    public ResponseEntity<CarrierResponse> create(@Valid @RequestBody CreateCarrierRequest request) {
        CarrierResponse response = CarrierResponse.from(carrierService.create(request.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}