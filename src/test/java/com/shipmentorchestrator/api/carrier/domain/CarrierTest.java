package com.shipmentorchestrator.api.carrier.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CarrierTest {

    @Test
    void shouldStartAsActive() {
        Carrier carrier = Carrier.create("Carrier", "12345678000199");

        assertTrue(carrier.isActive());
        assertEquals("Carrier", carrier.getName());
        assertEquals("12345678000199", carrier.getCnpj());
    }

    @Test
    void shouldUpdateNameAndActiveStatus() {
        Carrier carrier = Carrier.create("Carrier", "12345678000199");

        carrier.update("Updated Carrier", false);

        assertEquals("Updated Carrier", carrier.getName());
        assertEquals("12345678000199", carrier.getCnpj());
        assertTrue(!carrier.isActive());
    }

    @Test
    void shouldDeactivateWithoutRemovingCarrierData() {
        Carrier carrier = Carrier.create("Carrier", "12345678000199");

        carrier.deactivate();

        assertTrue(!carrier.isActive());
        assertEquals("Carrier", carrier.getName());
        assertEquals("12345678000199", carrier.getCnpj());
    }

}