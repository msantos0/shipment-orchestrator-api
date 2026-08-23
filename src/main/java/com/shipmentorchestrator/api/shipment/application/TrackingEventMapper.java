package com.shipmentorchestrator.api.shipment.application;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.shipment.domain.TrackingEvent;

@Mapper(componentModel = "spring")
public interface TrackingEventMapper {

    TrackingEventOutput toOutput(TrackingEvent trackingEvent);
}
