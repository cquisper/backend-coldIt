package com.coldIt.backend.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "facturas")
public class Factura {
    @Id
    private String id;

    private String cliente;

    private List<Item> items;

    private Double valorTotal;

    private LocalDateTime fecha;
}
