package com.shipmentorchestrator.api.shipment.application;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.shipment.domain.Shipment;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    Shipment toDomain(CreateShipmentCommand command);

    ShipmentOutput toOutput(Shipment shipment);
}
