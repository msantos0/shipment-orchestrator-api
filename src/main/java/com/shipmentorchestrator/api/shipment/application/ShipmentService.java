package com.shipmentorchestrator.api.shipment.application;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public ShipmentOutput create(CreateShipmentCommand command) {
        Shipment shipment = shipmentMapper.toDomain(command);
        Shipment newShipment = Shipment.builder()
                .origin(shipment.getOrigin())
                .destination(shipment.getDestination())
                .status(ShipmentStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        return shipmentMapper.toOutput(shipmentRepository.save(newShipment));
    }

    public List<ShipmentOutput> findAll() {
        return shipmentRepository.findAll().stream()
                .map(shipmentMapper::toOutput)
                .toList();
    }

    public ShipmentOutput findById(String id) {
        return shipmentRepository.findById(id)
                .map(shipmentMapper::toOutput)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
    }
}
