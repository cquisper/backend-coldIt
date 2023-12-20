package com.coldIt.backend.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Builder //Construye un objeto a partir de otro
@AllArgsConstructor //Crea un constructor con todos los argumentos
@NoArgsConstructor //Crea un constructor vacio
@Document(collection = "productos")
public class Producto {

    @Id
    private String id;

    private String codigo;

    private Double valor;

    private String nombre;
}
