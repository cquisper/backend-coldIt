package com.coldIt.backend.app.services;

import com.coldIt.backend.app.models.dto.FacturaRequest;
import com.coldIt.backend.app.models.dto.FacturaResponse;
import com.coldIt.backend.app.models.entities.Factura;
import com.coldIt.backend.app.models.entities.Producto;
import com.coldIt.backend.app.repositories.ItemRepository;
import com.coldIt.backend.app.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service @Slf4j
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;

    private final ItemRepository itemRepository;

    private final ProductoRepository productoRepository;

    public Flux<FacturaResponse> listarFacturas(){
        return facturaRepository.findAll()
                .map(entityToDto());
    }

    public Mono<FacturaResponse> calcularFactura(FacturaRequest facturaRequest){
        return Flux.fromIterable(facturaRequest.items())
                .flatMap(item -> {
                    //Calculo del valor total por item
                    return productoRepository.findByCodigo(item.getProducto().getCodigo())
                            .doOnNext(item::setProducto)
                            .map(producto -> producto.getValor() * item.getCantidad())
                            .doOnNext(item::setValorTotal)
                            .flatMap(valorTotal -> itemRepository.save(item)
                                    .thenReturn(valorTotal));
                })
                .reduce(Double::sum)//Se suman todos los valores totales por item
                .map(valorTotal -> Factura.builder() //Se crea la factura
                        .cliente(facturaRequest.cliente()) //Se setea el cliente
                        .items(facturaRequest.items()) //Se setean los items
                        .valorTotal(valorTotal) //Se setea el valor total
                        .fecha(LocalDateTime.now()) //Se setea la fecha
                        .build() //Se construye la factura
                )
                .flatMap(this.facturaRepository::save) //Se guarda la factura en MongoDB
                .doOnSuccess(fact -> log.info("Factura creada: {}", fact))
                .map(entityToDto());
    }

    private static Function<Factura, FacturaResponse> entityToDto() {
        return fact -> FacturaResponse.builder() //Se construye la respuesta
                .id(fact.getId())
                .cliente(fact.getCliente())
                .items(fact.getItems())
                .valorTotal(fact.getValorTotal())
                .fecha(fact.getFecha().toString())
                .build();
    }
}
