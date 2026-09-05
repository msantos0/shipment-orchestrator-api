package com.shipmentorchestrator.api.carrier.application;

import org.mapstruct.Mapper;

import com.shipmentorchestrator.api.carrier.domain.Carrier;

@Mapper(componentModel = "spring")
public interface CarrierMapper {

    CarrierOutput toOutput(Carrier carrier);
}