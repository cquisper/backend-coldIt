package com.coldIt.backend.app.models.dto;

import lombok.Builder;

@Builder
public record ProductoRequest(
        Double valor,
        String nombre) {
}
