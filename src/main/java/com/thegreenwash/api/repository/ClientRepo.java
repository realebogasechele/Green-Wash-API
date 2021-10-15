package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepo extends MongoRepository<Client, String> {
    Client findByCellNum(String cellNum);
    Optional<Client> findByCellNumAndPassword(String cellNum, String password);
    Client findByUnitNum(String unitNum);
}
