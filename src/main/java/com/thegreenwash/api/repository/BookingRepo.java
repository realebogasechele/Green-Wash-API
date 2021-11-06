package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface BookingRepo extends MongoRepository<Booking, String> {
    Optional<List<Booking>> findAllByClientId(String clientId);
    Optional<List<Booking>> findAllByAgentId(String agentId);

    List<Booking> findAllByComplexIdAndDateAndIsComplete(String complexId, String localDate, Boolean isComplete);

    List<Booking> findAllByDate(String localDate);

    List<Booking> findAllByPackageId(String packageId);
}
