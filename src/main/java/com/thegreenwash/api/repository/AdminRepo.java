package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Admin, String> {
}
