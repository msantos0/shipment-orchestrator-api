package com.shipmentorchestrator.api.shipment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public ShipmentOutput create(CreateShipmentCommand command) {
        Shipment shipment = Shipment.create(command.origin(), command.destination());
        return shipmentMapper.toOutput(shipmentRepository.save(shipment));
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
