package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Complex;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplexRepo extends MongoRepository<Complex, String> {
}
