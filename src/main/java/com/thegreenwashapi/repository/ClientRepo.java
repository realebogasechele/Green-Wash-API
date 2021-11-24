package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepo extends MongoRepository<Client, String> {
    Client findByCellNum(String cellNum);

    Client findByUnitNum(String unitNum);

    Optional<Client> findByClientIdAndIsDisabled(String clientId, boolean isDisabled);

    Client findByCellNumAndIsDisabledAndIsBlacklisted(String cellNum, boolean isDisabled, boolean isBlacklisted);

    Client findByEmailAndIsDisabledAndIsBlacklisted(String email, boolean isDisabled, boolean isBlacklisted);

    Client findByEmail(String email);
}
