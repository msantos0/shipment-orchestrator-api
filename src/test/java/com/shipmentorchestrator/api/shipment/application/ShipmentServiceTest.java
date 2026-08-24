package com.shipmentorchestrator.api.shipment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shipmentorchestrator.api.shipment.domain.Shipment;
import com.shipmentorchestrator.api.shipment.domain.ShipmentEvent;
import com.shipmentorchestrator.api.shipment.domain.ShipmentEventPublisher;
import com.shipmentorchestrator.api.shipment.domain.ShipmentRepository;
import com.shipmentorchestrator.api.shipment.domain.ShipmentStatus;
import com.shipmentorchestrator.api.shipment.domain.TrackingEvent;
import com.shipmentorchestrator.api.shipment.domain.TrackingEventType;
import com.shipmentorchestrator.api.shipment.domain.TrackingEventRepository;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @Mock
    private TrackingEventRepository trackingEventRepository;

    @Mock
    private TrackingEventMapper trackingEventMapper;

        @Mock
        private ShipmentEventPublisher shipmentEventPublisher;

    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    void createShouldRegisterPlannedEvent() {
        Shipment savedShipment = shipment("shipment-1", ShipmentStatus.PLANNED);
        when(shipmentRepository.save(org.mockito.ArgumentMatchers.any(Shipment.class))).thenReturn(savedShipment);
        when(trackingEventRepository.save(any(TrackingEvent.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ShipmentOutput output = new ShipmentOutput(
                "shipment-1", "origin", "destination", "tracking", ShipmentStatus.PLANNED, Instant.now());
        when(shipmentMapper.toOutput(savedShipment)).thenReturn(output);

        ShipmentOutput result = shipmentService.create(
                new CreateShipmentCommand("origin", "destination", "tracking"));

        assertThat(result).isEqualTo(output);
        verify(trackingEventRepository).save(argThat(event ->
                event.getShipmentId().equals("shipment-1")
                        && event.getEventType() == TrackingEventType.PLANNED));
        verify(shipmentEventPublisher).publish(argThat(event ->
                event.shipmentId().equals("shipment-1")
                        && event.status() == ShipmentStatus.PLANNED));
    }

    @Test
    void transitionShouldPublishShipmentEventAfterRegisteringTrackingEvent() {
        Shipment shipment = shipment("shipment-1", ShipmentStatus.PLANNED);
        when(shipmentRepository.findById("shipment-1")).thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment)).thenReturn(shipment);
        when(trackingEventRepository.save(any(TrackingEvent.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(shipmentMapper.toOutput(shipment)).thenReturn(null);

        shipmentService.pickup("shipment-1");

        verify(trackingEventRepository).save(argThat(event ->
                event.getShipmentId().equals("shipment-1")
                        && event.getEventType() == TrackingEventType.PICKED_UP));
        verify(shipmentEventPublisher).publish(argThat(event ->
                event.shipmentId().equals("shipment-1")
                        && event.status() == ShipmentStatus.PICKED_UP));
    }

    @Test
    void updateShouldNotRegisterOrPublishEvent() {
        Shipment shipment = shipment("shipment-1", ShipmentStatus.IN_TRANSIT);
        when(shipmentRepository.findById("shipment-1")).thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment)).thenReturn(shipment);
        when(shipmentMapper.toOutput(shipment)).thenReturn(null);

        shipmentService.update("shipment-1", new UpdateShipmentCommand(
                "origin", "destination", "tracking"));

        verify(trackingEventRepository, never()).save(org.mockito.ArgumentMatchers.any(TrackingEvent.class));
        verify(shipmentEventPublisher, never()).publish(any(ShipmentEvent.class));
    }

        @Test
        void invalidTransitionShouldNotRegisterOrPublishEvent() {
                Shipment shipment = shipment("shipment-1", ShipmentStatus.DELIVERED);
                when(shipmentRepository.findById("shipment-1")).thenReturn(Optional.of(shipment));

                assertThatThrownBy(() -> shipmentService.deliver("shipment-1"))
                                .isInstanceOf(RuntimeException.class);

                verify(shipmentRepository, never()).save(any(Shipment.class));
                verify(trackingEventRepository, never()).save(any(TrackingEvent.class));
                verify(shipmentEventPublisher, never()).publish(any(ShipmentEvent.class));
        }

    @Test
    void findTrackingEventsShouldMapEventsInRepositoryOrder() {
        Shipment shipment = shipment("shipment-1", ShipmentStatus.PLANNED);
        TrackingEvent first = TrackingEvent.create("shipment-1", ShipmentStatus.PLANNED, null, null);
        TrackingEvent second = TrackingEvent.create("shipment-1", ShipmentStatus.PICKED_UP, null, null);
        TrackingEventOutput firstOutput = new TrackingEventOutput(
                "event-1", "shipment-1", TrackingEventType.PLANNED, first.getEventDate(), null, null);
        TrackingEventOutput secondOutput = new TrackingEventOutput(
                "event-2", "shipment-1", TrackingEventType.PICKED_UP, second.getEventDate(), null, null);
        when(shipmentRepository.findById("shipment-1")).thenReturn(Optional.of(shipment));
        when(trackingEventRepository.findByShipmentId("shipment-1")).thenReturn(List.of(first, second));
        when(trackingEventMapper.toOutput(first)).thenReturn(firstOutput);
        when(trackingEventMapper.toOutput(second)).thenReturn(secondOutput);

        List<TrackingEventOutput> result = shipmentService.findTrackingEvents("shipment-1");

        assertThat(result).containsExactly(firstOutput, secondOutput);
    }

    private Shipment shipment(String id, ShipmentStatus status) {
        return Shipment.builder()
                .id(id)
                .origin("origin")
                .destination("destination")
                .trackingCode("tracking")
                .status(status)
                .createdAt(Instant.now())
                .build();
    }
}
