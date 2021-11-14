package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.BookingNotFoundException;
import com.thegreenwash.api.exception.ComplexNotFoundException;
import com.thegreenwash.api.exception.PackageNotFoundException;
import com.thegreenwash.api.model.Booking;
import com.thegreenwash.api.model.Client;
import com.thegreenwash.api.model.Complex;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public BookingService(ComplexRepo complexRepo, PackageRepo packageRepo, BookingRepo bookingRepo, AgentRepo agentRepo, ClientRepo clientRepo) {
        this.complexRepo = complexRepo;
        this.packageRepo = packageRepo;
        this.bookingRepo = bookingRepo;
        this.agentRepo = agentRepo;
    }

    public String addBooking(Booking booking){
        //Adds a booking to google calendar and booking database
        if(bookingRepo.findAllByDate(booking.getDate()).isEmpty()) {
            OffsetTime tempBookingTime = OffsetTime.parse(booking.getStartTime());
            OffsetDateTime tempBookingDate = OffsetDateTime.parse(booking.getDate());
            //Set a random agent from a complex
            booking.setAgentId(agentRepo.findBySurname(assignAgent(booking.getComplexId())).getAgentId());

            //Set the end time of the Booking
            booking.setEndTime(tempBookingTime.plusMinutes(packageRepo.findByPackageId(booking.getPackageId()).getMinutes()).plusMinutes(15).toString());

            //Ensures that the date is saved with just the date values for report generation
            booking.setDate(tempBookingDate.toLocalDate().toString());

            //Save Booking
            bookingRepo.save(booking);
            return "Booking added successfully!";
        }else{
            List<Booking> bookingsToday = bookingRepo.findAllByDate(booking.getDate());
            boolean bookingTimeFound = false;
            for (Booking book: bookingsToday) {
                OffsetDateTime startTime = OffsetDateTime.parse(booking.getStartTime());
                if(startTime.isBefore(OffsetDateTime.parse(book.getStartTime()))){
                    if(startTime.plusMinutes(packageRepo.findByPackageId(booking.getPackageId()).getMinutes()).plusMinutes(15).isBefore(OffsetDateTime.parse(book.getStartTime()))){
                        bookingTimeFound = true;
                        booking.setEndTime(startTime.plusMinutes(packageRepo.findByPackageId(booking.getPackageId()).getMinutes()).plusMinutes(15).toString());
                        break;
                    }
                }
            }
            if(bookingTimeFound == true){
                booking.setAgentId(agentRepo.findBySurname(assignAgent(booking.getComplexId())).getAgentId());
                bookingRepo.save(booking);
                return "Booking added successfully!";
            }
            else{
                return "Time is unavailable";
            }
        }
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

    public List<String> getSuggestedTimes(String complexName, String packageId, String date){
        ZonedDateTime date2 = ZonedDateTime.parse(date);
        Complex complex = complexRepo.findByComplexName(complexName).orElseThrow(()-> new ComplexNotFoundException("Not Found"));
        List<Booking> bookings = bookingRepo.findAllByComplexIdAndDateAndIsComplete(complex.getComplexId(), date2.toLocalDate().toString(), false);
        bookings.sort(Comparator.comparing(Booking::getStartTime));

        Package pack = packageRepo.findById(packageId)
                .orElseThrow(()-> new PackageNotFoundException("Not Found!"));
        List<String> suggestedTimes = new ArrayList<>();

        ZonedDateTime startTime = ZonedDateTime.parse(complex.getStartTime());
        if(Objects.isNull(bookings)){
            suggestedTimes.add(startTime.toOffsetDateTime().toOffsetTime().toString());
            suggestedTimes.add(startTime.plusMinutes(30).toOffsetDateTime().toOffsetTime().toString());
            suggestedTimes.add(startTime.plusMinutes(60).toOffsetDateTime().toOffsetTime().toString());
            suggestedTimes.add(startTime.plusMinutes(90).toOffsetDateTime().toOffsetTime().toString());
            return suggestedTimes;
        }
        else {
            ZonedDateTime endTime = ZonedDateTime.parse(complex.getEndTime());
            OffsetTime time = OffsetTime.now(ZoneId.of("Africa/Harare"));
            for (int i = 0; i < bookings.size(); i++) {
                OffsetTime currentBookingTime = OffsetDateTime.parse(bookings.get(i).getStartTime()).toOffsetTime();
                /*Checks if time is the same as the start of the complexes start time or before the complexes start time
                 * and before the time of the next booking*/
                if (time.equals(startTime) || time.isBefore(startTime.toOffsetDateTime().toOffsetTime())
                        && time.isBefore(currentBookingTime)) {
                    /*Checks if the next booking exists in order to compare the current booking with the next*/
                    if (!Objects.isNull(bookings.get(i + 1))) {
                        OffsetDateTime incrementedBookingTime = OffsetDateTime.parse(bookings.get(i + 1).getStartTime());
                        OffsetTime tempTime = time.plusMinutes(pack.getMinutes()).plusMinutes(15);
                        if (time.isBefore(incrementedBookingTime.toOffsetTime())) {
                            suggestedTimes.add(time.minusMinutes(pack.getMinutes() + 15).toString());
                        }
                    } else {
                        /*Checks if a booking can be made before the end of working time*/
                        time = time.plusMinutes(pack.getMinutes()).plusMinutes(15);
                        if (time.isBefore(endTime.toOffsetDateTime().toOffsetTime())) {
                            suggestedTimes.add(time.minusMinutes(pack.getMinutes() + 15).toString());
                        }
                    }
                }
            }
        }
        return suggestedTimes;
    }

    public Booking findById(String bookingId){
        return bookingRepo.findById(bookingId).orElseThrow(()-> new BookingNotFoundException("Booking does not exist!"));
    }

    public List<Booking> clientViewBookings(String clientId){
        return bookingRepo.findAllByClientId(clientId).orElseThrow(()-> new BookingNotFoundException("No Bookings!"));
    }

    public List<Booking> agentViewBookings(String agentId){
        return (bookingRepo.findAllByAgentId(agentId)).orElseThrow(()-> new BookingNotFoundException("No Bookings!"));
    }

    public List<Booking> adminViewBookings() {
        return bookingRepo.findAll();
    }

    public String inCompleteBooking(Booking booking){
        booking.setIncomplete(true);
        bookingRepo.save(booking);
        return "Incompletion Filed!";
    }

    public String completeBooking(String bookingId){
        try {
            Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Not Found!"));
            booking.setComplete(true);
            bookingRepo.save(booking);
            return "Completed booking filed!";
        }catch (Exception ex){
            return "Booking Not Found!";
        }
    }

    public List<Booking> getBookings(String date) {
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        return bookingRepo.findAllByDate(dateTime.toLocalDate().toString());
    }

    //Queries
    public Map<String, Object> getSortedBookings(int pageNo, int pageSize, String sortBy) {
        Map<String, Object> response = new HashMap<>();

        Sort sort = Sort.by(sortBy);
        Pageable page = PageRequest.of(pageNo,pageSize, sort);
        Page<Booking> bookingPage = bookingRepo.findAll(page);
        response.put("data", bookingPage.getContent());
        response.put("Total Number of Pages", bookingPage.getTotalPages());
        response.put("Total Number of Elements", bookingPage.getTotalElements());
        response.put("Current Page Number", bookingPage.getNumber());

        return response;
    }




}
