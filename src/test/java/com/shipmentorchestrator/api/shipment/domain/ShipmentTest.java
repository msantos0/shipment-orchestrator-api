package com.shipmentorchestrator.api.shipment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ShipmentTest {

    @Test
    void shouldFollowTheCompleteShipmentFlow() {
        Shipment shipment = shipment();

        shipment.plan();
        assertEquals(ShipmentStatus.PLANNED, shipment.getStatus());

        shipment.pickup();
        assertEquals(ShipmentStatus.PICKED_UP, shipment.getStatus());

        shipment.startTransit();
        assertEquals(ShipmentStatus.IN_TRANSIT, shipment.getStatus());

        shipment.deliver();
        assertEquals(ShipmentStatus.DELIVERED, shipment.getStatus());
    }

    @Test
    void shouldCancelCreatedShipment() {
        Shipment shipment = shipment();

        shipment.cancel();

        assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());
    }

    @Test
    void shouldCancelPlannedShipment() {
        Shipment shipment = shipment();
        shipment.plan();

        shipment.cancel();

        assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());
    }

    @Test
    void shouldRejectInvalidTransitions() {
        assertInvalidTransition(shipment(), Shipment::pickup);
        assertInvalidTransition(shipment(), Shipment::startTransit);
        assertInvalidTransition(shipment(), Shipment::deliver);

        Shipment planned = shipment();
        planned.plan();
        assertInvalidTransition(planned, Shipment::plan);
        assertInvalidTransition(planned, Shipment::startTransit);
        assertInvalidTransition(planned, Shipment::deliver);

        Shipment pickedUp = shipment();
        pickedUp.plan();
        pickedUp.pickup();
        assertInvalidTransition(pickedUp, Shipment::plan);
        assertInvalidTransition(pickedUp, Shipment::pickup);
        assertInvalidTransition(pickedUp, Shipment::deliver);
        assertInvalidTransition(pickedUp, Shipment::cancel);

        Shipment inTransit = shipment();
        inTransit.plan();
        inTransit.pickup();
        inTransit.startTransit();
        assertInvalidTransition(inTransit, Shipment::plan);
        assertInvalidTransition(inTransit, Shipment::pickup);
        assertInvalidTransition(inTransit, Shipment::startTransit);
        assertInvalidTransition(inTransit, Shipment::cancel);

        Shipment delivered = shipment();
        delivered.plan();
        delivered.pickup();
        delivered.startTransit();
        delivered.deliver();
        assertInvalidTransition(delivered, Shipment::cancel);

        Shipment cancelled = shipment();
        cancelled.cancel();
        assertInvalidTransition(cancelled, Shipment::plan);
        assertInvalidTransition(cancelled, Shipment::cancel);
    }

    private static void assertInvalidTransition(Shipment shipment, ShipmentAction action) {
        assertThrows(BusinessException.class, () -> action.apply(shipment));
    }

    private static Shipment shipment() {
        return Shipment.create("Origin", "Destination", "TRACK-001");
    }

    @FunctionalInterface
    private interface ShipmentAction {
        void apply(Shipment shipment);
    }
}