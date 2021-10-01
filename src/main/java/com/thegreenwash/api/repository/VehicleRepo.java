package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepo extends MongoRepository<Vehicle, String> {
}
