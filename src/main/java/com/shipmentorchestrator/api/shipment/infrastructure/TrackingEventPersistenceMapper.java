package com.shipmentorchestrator.api.shipment.infrastructure;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.shipment.domain.TrackingEvent;

@Mapper(componentModel = "spring")
public interface TrackingEventPersistenceMapper {

    TrackingEventDocument toDocument(TrackingEvent trackingEvent);

    TrackingEvent toDomain(TrackingEventDocument document);
}
