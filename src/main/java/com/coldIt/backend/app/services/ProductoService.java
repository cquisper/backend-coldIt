package com.coldIt.backend.app.services;

import com.coldIt.backend.app.models.dto.ProductoRequest;
import com.coldIt.backend.app.models.dto.ProductoResponse;
import com.coldIt.backend.app.models.entities.Producto;
import com.coldIt.backend.app.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service @Slf4j //Anotado como un servicio y se crea un logger
@RequiredArgsConstructor //Crea un constructor con atributos finales
public class ProductoService {

    private final ProductoRepository productoRepository; //Inyeccion de dependencias

    public Flux<ProductoResponse> listarProductos(){
        return this.productoRepository.findAll() //Se obtienen todos los productos de MongoDB
                .doOnNext(prod -> log.info("Producto obtenido: {}", prod))
                .map(entityToDto()); //Se mapean los productos a la respuesta
    }

    public Mono<ProductoResponse> crearProducto(ProductoRequest productoRequest){
        //Se crea el producto
        return this.productoRepository.save(Producto.builder()
                        .codigo(productoRequest.nombre().replaceAll(" ", "").toUpperCase() + UUID.randomUUID())
                        .nombre(productoRequest.nombre())
                        .valor(productoRequest.valor())
                        .build()
                ) //Se guarda el producto en MongoDB
                .doOnSuccess(prod -> log.info("Producto creado: {}", prod))
                .map(entityToDto()); //Se mapea el producto a la respuesta
    }

    public Mono<ProductoResponse> editarProducto(ProductoRequest productoRequest, String id){
        return this.productoRepository.findById(id) //Se busca el producto por id
                .doOnNext(prod -> { //Se setean los nuevos valores
                    prod.setNombre(productoRequest.nombre());
                    prod.setValor(productoRequest.valor());
                })
                .flatMap(this.productoRepository::save) //Se guarda el producto con sus nyevos valores en MongoDB
                .doOnSuccess(prod -> log.info("Producto editado: {}", prod))
                .map(entityToDto()); //Se mapea el producto a la respuesta
    }

    public Mono<Void> eliminarProducto(String id){
        return this.productoRepository.findById(id) //Se busca el producto por id
                .flatMap(this.productoRepository::delete) //Se elimina el producto de MongoDB
                .doOnSuccess(prod -> log.info("Producto eliminado: {}", prod));
    }

    private static Function<Producto, ProductoResponse> entityToDto() {
        //Metodo estatico y privado que mapea el producto a la respuesta a traves de la interfaz functional Function
        return producto -> ProductoResponse.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .valor(producto.getValor())
                .build();
    }
}
