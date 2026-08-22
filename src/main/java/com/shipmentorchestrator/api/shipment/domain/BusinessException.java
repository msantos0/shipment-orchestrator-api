package com.shipmentorchestrator.api.shipment.domain;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}