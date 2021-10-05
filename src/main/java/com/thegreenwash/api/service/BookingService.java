package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.ComplexNotFoundException;
import com.thegreenwash.api.exception.PackageNotFoundException;
import com.thegreenwash.api.model.Booking;
import com.thegreenwash.api.model.Complex;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.AgentRepo;
import com.thegreenwash.api.repository.BookingRepo;
import com.thegreenwash.api.repository.ComplexRepo;
import com.thegreenwash.api.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;

@Service
public class BookingService {
    private final ComplexRepo complexRepo;
    private final PackageRepo packageRepo;
    private final BookingRepo bookingRepo;
    private final AgentRepo agentRepo;

    @Autowired
    public BookingService(ComplexRepo complexRepo, PackageRepo packageRepo, BookingRepo bookingRepo, AgentRepo agentRepo) {
        this.complexRepo = complexRepo;
        this.packageRepo = packageRepo;
        this.bookingRepo = bookingRepo;
        this.agentRepo = agentRepo;
    }

    public String addBooking(Booking booking){
        //Adds a booking to google calendar and booking database
        booking.setAgentId(assignAgent(booking.getComplexId()));
        return "completeness status(Complete/Incomplete)";
    }

    public String assignAgent(String complexId){
        //Assigns an agent and returns agentId
        Complex complex = complexRepo.findById(complexId).orElseThrow(
                ()-> new ComplexNotFoundException("Not found !"));
        Random random = new Random();
        //Returns a random agentId
        return (complex.getAgents()
                .get(random.nextInt(complex.getAgents().size())));
    }

    public List<LocalTime> getSuggestedTimes(String complexId, String packageId, LocalDate date){
        List<Booking> bookings = bookingRepo.findAllByComplexIdAndDateAndIsComplete(complexId, date, false);
        bookings.sort(Comparator.comparing(Booking::getStartTime));

        Package pack = packageRepo.findById(packageId)
                .orElseThrow(()-> new PackageNotFoundException("Not Found!"));
        Complex complex = complexRepo.findById(complexId)
                .orElseThrow(()-> new ComplexNotFoundException("There is no complex with the id: "+ complexId));

        List<LocalTime> suggestedTimes = new ArrayList<>();

        if(Objects.isNull(bookings)){
            suggestedTimes.add(complex.getStartTime());
            suggestedTimes.add(complex.getStartTime().plusMinutes(30));
            suggestedTimes.add(complex.getStartTime().plusMinutes(60));
            suggestedTimes.add(complex.getStartTime().plusMinutes(90));
            return suggestedTimes;
        }
        else {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Africa/Harare"));
            LocalTime time = now.toLocalTime();
            for (int i = 0; i < suggestedTimes.size(); i++) {
                LocalTime currentBookingTime = bookings.get(i).getStartTime().toLocalTime();
                /*Checks if time is the same as the start of the complexes start time or before the complexes start time
                 * and before the time of the next booking*/
                if (time.equals(complex.getStartTime()) || time.isBefore(complex.getStartTime())
                        && time.isBefore(currentBookingTime)) {
                    /*Checks if the next booking exists in order to compare the current booking with the next*/
                    if (!Objects.isNull(bookings.get(i + 1))) {
                        time = time.plusMinutes(pack.getMinutes()).plusMinutes(15);
                        if (time.isBefore(bookings.get(i + 1).getStartTime().toLocalTime())) {
                            suggestedTimes.add(time.minusMinutes(pack.getMinutes() + 15));
                        }
                    } else {
                        /*Checks if a booking can be made before the end of working time*/
                        time = time.plusMinutes(pack.getMinutes()).plusMinutes(15);
                        if (time.isBefore(complex.getEndTime())) {
                            suggestedTimes.add(time.minusMinutes(pack.getMinutes() + 15));
                        }
                    }
                }
            }
        }
        return suggestedTimes;
    }

    public List<Booking> clientViewBookings(String clientId){
        return bookingRepo.findAllByClientId(clientId);
    }

    public List<Booking> agentViewBookings(String agentId){
        return (bookingRepo.findAllByAgentId(agentId));
    }

}
