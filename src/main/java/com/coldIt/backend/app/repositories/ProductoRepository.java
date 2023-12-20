package com.coldIt.backend.app.repositories;

import com.coldIt.backend.app.models.entities.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

    Mono<Producto> findByCodigo(String codigo);
}
