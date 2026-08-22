package com.shipmentorchestrator.api.shipment.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    Page<Shipment> findAll(ShipmentStatus status, String trackingCode, Pageable pageable);

    Optional<Shipment> findById(String id);

    void deleteById(String id);
}
