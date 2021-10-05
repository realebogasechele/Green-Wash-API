package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo extends MongoRepository<Promotion,String> {
    Promotion findByPackageId(String packageId);
}

