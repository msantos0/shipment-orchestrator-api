package com.shipmentorchestrator.api.shipment.application;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.shipment.domain.Shipment;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    ShipmentOutput toOutput(Shipment shipment);
}
