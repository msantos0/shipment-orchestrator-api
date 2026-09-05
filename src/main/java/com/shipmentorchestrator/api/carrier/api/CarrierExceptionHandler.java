package com.shipmentorchestrator.api.carrier.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shipmentorchestrator.api.carrier.application.CarrierNotFoundException;

@RestControllerAdvice
public class CarrierExceptionHandler {

    @ExceptionHandler(CarrierNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(CarrierNotFoundException exception) {
        return Map.of("error", exception.getMessage());
    }
}