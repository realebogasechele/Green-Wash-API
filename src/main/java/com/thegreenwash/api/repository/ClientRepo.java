package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepo extends MongoRepository<Client, String> {
    Client findByCellNum(String cellNum);
    Client findByDeviceId(String deviceId);
    Client findByCellNumAndPasswordAndDeviceId(String cellNum, String password, String deviceId);
    Optional<Client> findByCellNumAndPassword(String cellNum, String password);
}
