package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Optional<Admin> findByCellNumAndPassword(String cellNum, String password);

    Admin findByCellNum(String cellNum);

    Admin findByEmail(String email);

    Optional<Admin> findByEmailAndPassword(String email, String password);
}
