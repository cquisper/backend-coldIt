package com.coldIt.backend.app.services;

import com.coldIt.backend.app.models.entities.Factura;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends ReactiveMongoRepository<Factura, String> {
}
