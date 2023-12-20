package com.coldIt.backend.app.models.dto;

import lombok.Builder;

@Builder
public record ProductoResponse(
        String id,
        String codigo,
        Double valor,
        String nombre) {
}
