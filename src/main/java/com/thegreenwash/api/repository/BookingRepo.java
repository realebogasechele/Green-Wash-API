package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepo extends MongoRepository<Booking, String> {
    List<Booking> findAllByClientId(String clientId);
    List<Booking> findAllByAgentId(String agentId);

    List<Booking> findAllByComplexIdAndDateAndIsComplete(String complexId, LocalDate localDate, Boolean isComplete);
}
