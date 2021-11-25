package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepo extends MongoRepository<Agent, String> {
    Optional<Agent> findByCellNumAndPassword(String cellNum, String password);

   Agent findByCellNum(String cellNum);

    Agent findBySurname(String surname);

    Optional<Agent> findByAgentIdAndIsDisabled(String agentId, boolean disabled);

    List<Agent> findAllByIsDisabled(boolean isDisabled);

    Agent findByCellNumAndIsDisabled(String cellNum, boolean isDisabled);
}
