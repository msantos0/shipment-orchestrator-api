package com.shipmentorchestrator.api.shipment.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shipmentorchestrator.api.shipment.domain.TrackingEvent;
import com.shipmentorchestrator.api.shipment.domain.TrackingEventRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TrackingEventRepositoryAdapter implements TrackingEventRepository {

    private final TrackingEventMongoRepository trackingEventMongoRepository;
    private final TrackingEventPersistenceMapper trackingEventPersistenceMapper;

    @Override
    public TrackingEvent save(TrackingEvent trackingEvent) {
        TrackingEventDocument document = trackingEventPersistenceMapper.toDocument(trackingEvent);
        return trackingEventPersistenceMapper.toDomain(trackingEventMongoRepository.save(document));
    }

    @Override
    public List<TrackingEvent> findByShipmentId(String shipmentId) {
        return trackingEventMongoRepository.findByShipmentIdOrderByEventDateAsc(shipmentId).stream()
                .map(trackingEventPersistenceMapper::toDomain)
                .toList();
    }
}
