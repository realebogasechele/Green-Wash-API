package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Packages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagesRepo extends MongoRepository <Packages, String> {
}
