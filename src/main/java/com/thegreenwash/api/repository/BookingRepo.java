package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface BookingRepo extends MongoRepository<Booking, String> {
    Optional<List<Booking>> findAllByClientId(String clientId);

    Optional<List<Booking>> findAllByAgentId(String agentId);

    List<Booking> findAllByDate(String localDate);

    List<Booking> findAllByPackageName(String packageId);

    List<Booking> findAllByAgentIdAndDate(String agentId, String toString);

    List<Booking> findAllByComplexNameAndDateAndIsComplete(String complexId, String toString, boolean isComplete);
}
