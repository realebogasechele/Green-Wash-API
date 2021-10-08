package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepo extends MongoRepository<Vehicle, String> {
    Optional<List<Vehicle>> findAllByClientId(String clientId);
}
