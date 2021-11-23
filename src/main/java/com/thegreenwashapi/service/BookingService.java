package com.thegreenwashapi.service;

import com.thegreenwashapi.exception.BookingNotFoundException;
import com.thegreenwashapi.exception.ClientNotFoundException;
import com.thegreenwashapi.exception.ComplexNotFoundException;
import com.thegreenwashapi.exception.PackageNotFoundException;
import com.thegreenwashapi.model.Booking;
import com.thegreenwashapi.model.Client;
import com.thegreenwashapi.model.Complex;
import com.thegreenwashapi.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class BookingService {
    private final ComplexRepo complexRepo;
    private final PackageRepo packageRepo;
    private final BookingRepo bookingRepo;
    private final AgentRepo agentRepo;
    private final ClientRepo clientRepo;

    @Autowired
    public BookingService(ComplexRepo complexRepo, PackageRepo packageRepo, BookingRepo bookingRepo, AgentRepo agentRepo, ClientRepo clientRepo) {
        this.complexRepo = complexRepo;
        this.packageRepo = packageRepo;
        this.bookingRepo = bookingRepo;
        this.agentRepo = agentRepo;
        this.clientRepo = clientRepo;
    }

    public String addBooking(@NotNull Booking booking) {
        OffsetDateTime startTime = OffsetDateTime.parse(booking.getStartTime());
        List<Booking> bookings = bookingRepo.findAllByDate(startTime.toLocalDate().toString());
        Client client = clientRepo.findById(booking.getClientId())
                .orElseThrow(()-> new ClientNotFoundException("Not Found."));

        if (bookings.isEmpty()) {
            booking.setAgentId(assignAgent(booking.getComplexName()));
            booking.setEndTime(startTime
                    .plusMinutes(15)
                    .plusMinutes(packageRepo.findByPackageName(booking.getPackageName())
                            .orElseThrow(()-> new PackageNotFoundException("Not Found."))
                            .getMinutes())
                            .toString());
            booking.setDate(startTime.toLocalDate().toString());
            booking.setClientName(client.getName());
            booking.setClientSurname(client.getSurname());
            bookingRepo.save(booking);
            return "Success";
        } else {
            List<String> times = new ArrayList<>();
            for (Booking book : bookings) {
                times.add(book.getStartTime());
            }
            int index = Collections.binarySearch(times, booking.getStartTime());
            if (index >= 0) {
                return "error";
            } else {
                booking.setAgentId(assignAgent(booking.getComplexName()));
                booking.setEndTime(startTime
                        .plusMinutes(15)
                        .plusMinutes(packageRepo.findByPackageName(booking.getPackageName())
                                .orElseThrow(() -> new PackageNotFoundException("Not Found."))
                                .getMinutes())
                                .toString());
                booking.setDate(startTime.toLocalDate().toString());
                booking.setClientName(client.getName());
                booking.setClientSurname(client.getSurname());
                bookingRepo.save(booking);
                return "Success";
            }
        }
    }

    public String assignAgent(String complexName) {
        //Assigns an agent and returns agentId
        Complex complex = complexRepo.findByComplexName(complexName).orElseThrow(
                () -> new ComplexNotFoundException("Not found !"));
        Random random = new Random();
        //Returns a random agentId
        String agentSurname = complex.getAgents()
                .get(random.nextInt(complex.getAgents().size()));
        return agentRepo.findBySurname(agentSurname).getAgentId();
    }

    public List<String> getSuggestedTimes(String complexName, String date) {
        Complex complex = complexRepo.findByComplexName(complexName).orElseThrow(() -> new ComplexNotFoundException("Not Found"));
        OffsetDateTime selectedDate = OffsetDateTime.parse(date);
        List<Booking> bookings = bookingRepo
                .findAllByComplexNameAndDateAndIsComplete(complex.getComplexId(), selectedDate.toLocalDate().toString(), false);
        OffsetTime complexStartTime = OffsetDateTime.parse(complex.getStartTime()).toOffsetTime();
        OffsetTime complexEndTime = OffsetDateTime.parse(complex.getEndTime()).toOffsetTime();
        List<OffsetTime> bookingTimes = new ArrayList<>();
        for (Booking book : bookings) {
            bookingTimes.add(OffsetTime.parse(book.getStartTime()));
        }
        Collections.sort(bookingTimes);
        List<OffsetTime> suggestedTimes = new ArrayList<>();

        suggestedTimes = fillList(complexStartTime, complexEndTime, suggestedTimes);

        OffsetTime now = OffsetDateTime.parse(date).toOffsetTime();

        suggestedTimes.removeIf(now::isAfter);

        for (OffsetTime bookingTime : bookingTimes) {
            int index = Collections.binarySearch(suggestedTimes, bookingTime);
            if (index >= 0) {
                suggestedTimes.remove(index);
            }
        }

        List<String> availableTimes = new ArrayList<>();
        for (OffsetTime suggestedTime : suggestedTimes) {
            OffsetDateTime time = OffsetDateTime.parse(date);
            time = time.withHour(suggestedTime.getHour());
            time = time.withMinute(suggestedTime.getMinute());
            time = time.withSecond(suggestedTime.getSecond());
            time = time .withNano(0);
            availableTimes.add(time.toString());
        }
        return availableTimes;
    }

    private List<OffsetTime> fillList(OffsetTime complexStartTime, OffsetTime complexEndTime, List<OffsetTime> suggestedTimes) {
        if (complexStartTime.equals(complexEndTime) || complexStartTime.isAfter(complexEndTime)) {
            return suggestedTimes;
        } else {
            suggestedTimes.add(complexStartTime);
            return fillList(complexStartTime.plusHours(1), complexEndTime, suggestedTimes);
        }
    }

    public Booking findById(String bookingId) {
        return bookingRepo.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking does not exist!"));
    }

    public List<Booking> clientViewBookings(String clientId) {
        return bookingRepo.findAllByClientId(clientId).orElseThrow(() -> new BookingNotFoundException("No Bookings!"));
    }

    public List<Booking> agentViewBookings(String agentId) {
        return (bookingRepo.findAllByAgentId(agentId)).orElseThrow(() -> new BookingNotFoundException("No Bookings!"));
    }

    public List<Booking> adminViewBookings() {
        return bookingRepo.findAll();
    }

    public String inCompleteBooking(Booking booking) {
        booking.setIncomplete(true);
        bookingRepo.save(booking);
        return "Incomplete Booking Filed!";
    }

    public String completeBooking(String bookingId) {
        try {
            Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Not Found!"));
            booking.setComplete(true);
            bookingRepo.save(booking);
            return "Completed booking filed!";
        } catch (Exception ex) {
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
        Pageable page = PageRequest.of(pageNo, pageSize, sort);
        Page<Booking> bookingPage = bookingRepo.findAll(page);
        response.put("data", bookingPage.getContent());
        response.put("Total Number of Pages", bookingPage.getTotalPages());
        response.put("Total Number of Elements", bookingPage.getTotalElements());
        response.put("Current Page Number", bookingPage.getNumber());

        return response;
    }


}
