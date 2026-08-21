package com.shipmentorchestrator.api.shipment.domain;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    List<Shipment> findAll();

    Optional<Shipment> findById(String id);
}
