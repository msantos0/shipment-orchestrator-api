package com.shipmentorchestrator.api.shipment.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;
import com.shipmentorchestrator.api.shipment.domain.TrackingEvent;
import com.shipmentorchestrator.api.shipment.domain.TrackingEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private final TrackingEventRepository trackingEventRepository;
    private final TrackingEventMapper trackingEventMapper;

    public ShipmentOutput create(CreateShipmentCommand command) {
        Shipment shipment = Shipment.create(command.origin(), command.destination(), command.trackingCode());
        Shipment savedShipment = shipmentRepository.save(shipment);
        trackingEventRepository.save(TrackingEvent.create(
            savedShipment.getId(), savedShipment.getStatus(), null, null));
        return shipmentMapper.toOutput(savedShipment);
    }

    public Page<ShipmentOutput> findAll(ShipmentStatus status, String trackingCode, Pageable pageable) {
        return shipmentRepository.findAll(status, trackingCode, pageable)
                .map(shipmentMapper::toOutput);
    }

    public ShipmentOutput update(String id, UpdateShipmentCommand command) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        ShipmentStatus previousStatus = shipment.getStatus();
        shipment.update(command.origin(), command.destination(), command.trackingCode(), command.status());
        Shipment savedShipment = shipmentRepository.save(shipment);
        if (previousStatus != command.status()) {
            trackingEventRepository.save(TrackingEvent.create(
                savedShipment.getId(), savedShipment.getStatus(), null, null));
        }
        return shipmentMapper.toOutput(savedShipment);
    }

    public ShipmentOutput plan(String id) {
        return transition(id, ShipmentStatus.PLANNED);
    }

    public ShipmentOutput pickup(String id) {
        return transition(id, ShipmentStatus.PICKED_UP);
    }

    public ShipmentOutput startTransit(String id) {
        return transition(id, ShipmentStatus.IN_TRANSIT);
    }

    public ShipmentOutput deliver(String id) {
        return transition(id, ShipmentStatus.DELIVERED);
    }

    public ShipmentOutput cancel(String id) {
        return transition(id, ShipmentStatus.CANCELLED);
    }

    private ShipmentOutput transition(String id, ShipmentStatus targetStatus) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        ShipmentStatus previousStatus = shipment.getStatus();
        shipment.update(
                shipment.getOrigin(), shipment.getDestination(), shipment.getTrackingCode(), targetStatus);
        Shipment savedShipment = shipmentRepository.save(shipment);
        if (previousStatus != targetStatus) {
            trackingEventRepository.save(TrackingEvent.create(
                    savedShipment.getId(), savedShipment.getStatus(), null, null));
        }
        return shipmentMapper.toOutput(savedShipment);
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

    public List<TrackingEventOutput> findTrackingEvents(String id) {
        shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        return trackingEventRepository.findByShipmentId(id).stream()
                .map(trackingEventMapper::toOutput)
                .toList();
    }
}
