package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Package;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Repository
public interface PackageRepo extends MongoRepository <Package, String> {
    List<Package> findAllByOnPromotion(boolean onPromotion);

    Optional<Package> findByPackageName(String packageName);

    Package findByPackageId(String packageId);
}
