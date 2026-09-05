package com.shipmentorchestrator.api.carrier.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {

    private String id;
    private String name;
    private String cnpj;
    private boolean active;
    private Instant createdAt;

    public static Carrier create(String name, String cnpj) {
        return Carrier.builder()
                .name(name)
                .cnpj(cnpj)
                .active(true)
                .createdAt(Instant.now())
                .build();
    }

    public void update(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public void deactivate() {
        this.active = false;
    }

}