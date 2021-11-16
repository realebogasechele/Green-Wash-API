package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Optional<Admin> findByCellNumAndPassword(String cellNum, String password);

    Admin findByCellNum(String cellNum);

    Admin findByEmail(String email);
}
