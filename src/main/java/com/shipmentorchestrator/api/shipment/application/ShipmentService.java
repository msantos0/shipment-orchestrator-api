package com.shipmentorchestrator.api.shipment.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Shipment shipment = Shipment.create(command.origin(), command.destination(), command.trackingCode());
        return shipmentMapper.toOutput(shipmentRepository.save(shipment));
    }

    public Page<ShipmentOutput> findAll(ShipmentStatus status, String trackingCode, Pageable pageable) {
        return shipmentRepository.findAll(status, trackingCode, pageable)
                .map(shipmentMapper::toOutput);
    }

    public ShipmentOutput update(String id, UpdateShipmentCommand command) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        shipment.update(command.origin(), command.destination(), command.trackingCode());
        return shipmentMapper.toOutput(shipmentRepository.save(shipment));
    }

    public ShipmentOutput plan(String id) {
        return transition(id, Shipment::plan);
    }

    public ShipmentOutput pickup(String id) {
        return transition(id, Shipment::pickup);
    }

    public ShipmentOutput startTransit(String id) {
        return transition(id, Shipment::startTransit);
    }

    public ShipmentOutput deliver(String id) {
        return transition(id, Shipment::deliver);
    }

    public ShipmentOutput cancel(String id) {
        return transition(id, Shipment::cancel);
    }

    private ShipmentOutput transition(String id, java.util.function.Consumer<Shipment> action) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        action.accept(shipment);
        return shipmentMapper.toOutput(shipmentRepository.save(shipment));
    }

    public void delete(String id) {
        shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        shipmentRepository.deleteById(id);
    }

    public ShipmentOutput findById(String id) {
        return shipmentRepository.findById(id)
                .map(shipmentMapper::toOutput)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
    }
}
