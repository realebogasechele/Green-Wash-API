package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo extends MongoRepository<Promotion,String> {
    Promotion findByPackageName(String packageName);
}

