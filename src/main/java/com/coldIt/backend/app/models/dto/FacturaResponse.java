package com.coldIt.backend.app.models.dto;

import com.coldIt.backend.app.models.entities.Item;
import lombok.Builder;

import java.util.List;
@Builder
public record FacturaResponse(
        String id,
        String cliente,
        List<Item> items,
        Double valorTotal,
        String fecha ) {
}
