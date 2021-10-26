package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Complex;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComplexRepo extends MongoRepository<Complex, String> {
    List<String> findAllByUnits(String complexId);

    Optional<Complex> findByComplexName(String complexName);
}
