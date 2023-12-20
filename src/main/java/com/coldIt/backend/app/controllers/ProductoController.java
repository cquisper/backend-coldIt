package com.coldIt.backend.app.controllers;

import com.coldIt.backend.app.models.dto.ProductoRequest;
import com.coldIt.backend.app.models.dto.ProductoResponse;
import com.coldIt.backend.app.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductoResponse> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductoResponse> crearProducto(@RequestBody ProductoRequest productoRequest) {
        return productoService.crearProducto(productoRequest);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoResponse> editarProducto(@RequestBody ProductoRequest productoRequest, @PathVariable String id) {
        return productoService.editarProducto(productoRequest, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> eliminarProducto(@PathVariable String id) {
        return productoService.eliminarProducto(id);
    }
}
