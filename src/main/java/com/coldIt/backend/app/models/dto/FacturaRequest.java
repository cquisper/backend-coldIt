package com.coldIt.backend.app.models.dto;

import com.coldIt.backend.app.models.entities.Item;
import lombok.Builder;

import java.util.List;

@Builder
public record FacturaRequest(
        String cliente,
        List<Item> items
) {
}
