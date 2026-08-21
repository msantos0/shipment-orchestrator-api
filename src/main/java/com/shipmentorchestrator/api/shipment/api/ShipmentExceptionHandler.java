package com.shipmentorchestrator.api.shipment.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shipmentorchestrator.api.shipment.application.ShipmentNotFoundException;

@RestControllerAdvice
public class ShipmentExceptionHandler {

    @ExceptionHandler(ShipmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(ShipmentNotFoundException exception) {
        return Map.of("error", exception.getMessage());
    }
}
