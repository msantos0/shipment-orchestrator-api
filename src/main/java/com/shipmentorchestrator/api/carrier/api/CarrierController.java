package com.shipmentorchestrator.api.carrier.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shipmentorchestrator.api.carrier.application.CarrierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Carriers", description = "Carrier management operations")
public class CarrierController {

    private final CarrierService carrierService;

    @PostMapping
    @Operation(summary = "Create a carrier", description = "Creates an active carrier")
    public ResponseEntity<CarrierResponse> create(@Valid @RequestBody CreateCarrierRequest request) {
        CarrierResponse response = CarrierResponse.from(carrierService.create(request.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a carrier", description = "Updates a carrier name and active status")
    public CarrierResponse update(
            @PathVariable String id, @Valid @RequestBody UpdateCarrierRequest request) {
        return CarrierResponse.from(carrierService.update(id, request.toCommand()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a carrier", description = "Deactivates a carrier without removing its historical record")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        carrierService.delete(id);
    }

    @GetMapping
    @Operation(summary = "List carriers", description = "Lists carriers with pagination and optional filters")
    public CarrierPageResponse findAll(
            @Parameter(description = "Case-insensitive partial carrier name filter")
            @RequestParam(required = false) String name,
            @Parameter(description = "Filter by active status")
            @RequestParam(required = false) Boolean active,
            @Parameter(description = "Zero-based page number")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return CarrierPageResponse.from(carrierService.findAll(name, active, pageable));
    }
}