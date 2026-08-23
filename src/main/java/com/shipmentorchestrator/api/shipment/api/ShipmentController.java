package com.shipmentorchestrator.api.shipment.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shipmentorchestrator.api.shipment.application.ShipmentService;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Shipments", description = "Shipment orchestration operations")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    @Operation(summary = "Create a shipment", description = "Creates a shipment with a tracking code")
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody CreateShipmentRequest request) {
        ShipmentResponse response = ShipmentResponse.from(shipmentService.create(request.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List shipments", description = "Lists shipments with pagination and optional filters")
    public ShipmentPageResponse findAll(
            @Parameter(description = "Filter by shipment status")
            @RequestParam(required = false) ShipmentStatus status,
            @Parameter(description = "Case-insensitive partial tracking code filter")
            @RequestParam(required = false) String trackingCode,
            @Parameter(description = "Zero-based page number")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ShipmentPageResponse.from(shipmentService.findAll(status, trackingCode, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a shipment by id")
    public ShipmentResponse findById(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.findById(id));
    }

    @GetMapping("/{id}/tracking-events")
    @Operation(summary = "List shipment tracking events")
    public List<TrackingEventResponse> findTrackingEvents(@PathVariable String id) {
        return shipmentService.findTrackingEvents(id).stream()
                .map(TrackingEventResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a shipment")
    public ShipmentResponse update(
            @PathVariable String id, @Valid @RequestBody UpdateShipmentRequest request) {
        return ShipmentResponse.from(shipmentService.update(id, request.toCommand()));
    }

    @PostMapping("/{id}/plan")
    @Operation(summary = "Plan a shipment")
    public ShipmentResponse plan(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.plan(id));
    }

    @PostMapping("/{id}/pickup")
    @Operation(summary = "Pick up a shipment")
    public ShipmentResponse pickup(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.pickup(id));
    }

    @PostMapping("/{id}/start-transit")
    @Operation(summary = "Start shipment transit")
    public ShipmentResponse startTransit(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.startTransit(id));
    }

    @PostMapping("/{id}/deliver")
    @Operation(summary = "Deliver a shipment")
    public ShipmentResponse deliver(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.deliver(id));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a shipment")
    public ShipmentResponse cancel(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.cancel(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shipment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        shipmentService.delete(id);
    }
}
