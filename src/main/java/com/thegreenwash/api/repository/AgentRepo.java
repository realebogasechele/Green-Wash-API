package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepo extends MongoRepository<Agent, String> {
    Optional<Agent> findByCellNumAndPassword(String cellNum, String password);

   Agent findByCellNum(String cellNum);
}
