package com.shipmentorchestrator.api.shipment.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shipmentorchestrator.api.shipment.application.ShipmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipments", description = "Shipment orchestration operations")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    @Operation(summary = "Create a shipment")
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody CreateShipmentRequest request) {
        ShipmentResponse response = ShipmentResponse.from(shipmentService.create(request.toCommand()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List shipments")
    public List<ShipmentResponse> findAll() {
        return shipmentService.findAll().stream()
                .map(ShipmentResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a shipment by id")
    public ShipmentResponse findById(@PathVariable String id) {
        return ShipmentResponse.from(shipmentService.findById(id));
    }
}
