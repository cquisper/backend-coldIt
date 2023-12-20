package com.coldIt.backend.app.controllers;

import com.coldIt.backend.app.models.dto.FacturaRequest;
import com.coldIt.backend.app.models.dto.FacturaResponse;
import com.coldIt.backend.app.services.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/factura")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public Flux<FacturaResponse> listarResponse() {
        return facturaService.listarFacturas();
    }

    @PostMapping("/calcular")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FacturaResponse> calcularFactura(@RequestBody FacturaRequest facturaRequest) {
        return facturaService.calcularFactura(facturaRequest);
    }
}
