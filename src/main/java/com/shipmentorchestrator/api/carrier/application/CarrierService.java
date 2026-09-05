package com.shipmentorchestrator.api.carrier.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shipmentorchestrator.api.carrier.domain.Carrier;
import com.shipmentorchestrator.api.carrier.domain.CarrierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarrierService.class);

    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    public CarrierOutput create(CreateCarrierCommand command) {
        Carrier savedCarrier = carrierRepository.save(Carrier.create(command.name(), command.cnpj()));
        LOGGER.info("Carrier created: {}", savedCarrier.getId());
        return carrierMapper.toOutput(savedCarrier);
    }
}