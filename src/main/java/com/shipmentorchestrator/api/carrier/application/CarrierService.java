package com.shipmentorchestrator.api.carrier.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public CarrierOutput findById(String id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new CarrierNotFoundException(id));
        LOGGER.info("Carrier retrieved: {}", id);
        return carrierMapper.toOutput(carrier);
    }

    public CarrierOutput update(String id, UpdateCarrierCommand command) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new CarrierNotFoundException(id));
        carrier.update(command.name(), command.active());
        Carrier savedCarrier = carrierRepository.save(carrier);
        LOGGER.info("Carrier updated: {}", savedCarrier.getId());
        return carrierMapper.toOutput(savedCarrier);
    }

    public void delete(String id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new CarrierNotFoundException(id));
        carrier.deactivate();
        carrierRepository.save(carrier);
        LOGGER.info("Carrier deactivated: {}", id);
    }

    public Page<CarrierOutput> findAll(String name, Boolean active, Pageable pageable) {
        return carrierRepository.findAll(name, active, pageable)
                .map(carrierMapper::toOutput);
    }
}