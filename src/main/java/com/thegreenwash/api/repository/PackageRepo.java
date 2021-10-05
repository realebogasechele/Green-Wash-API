package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Package;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepo extends MongoRepository <Package, String> {
    List<Package> findAllByOnPromotion(boolean onPromotion);
}
