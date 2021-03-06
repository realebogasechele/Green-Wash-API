package com.thegreenwashapi.controller;

import com.thegreenwashapi.model.Booking;
import com.thegreenwashapi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    //Agent Related
    @GetMapping("/login/{cellNum}/{password}")
    public ResponseEntity<String> login(@PathVariable("cellNum") String cellNum,
                                        @PathVariable("password") String password){
        String agentId = agentService.login(cellNum, password);
        return new ResponseEntity<>(agentId, HttpStatus.ACCEPTED);
    }
    //Booking Related
    @GetMapping("/booking/all/{agentId}")
    public ResponseEntity<List<Booking>> viewAllBookings(@PathVariable("agentId") String agentId){
        List<Booking> bookings = agentService.viewBookings(agentId);
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }

    @PostMapping("/booking/complete/{bookingId}")
    public ResponseEntity<String> completeBooking(@PathVariable("bookingId") String bookingId){
        String status = agentService.completeBooking(bookingId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @PostMapping("/booking/incomplete")
    public ResponseEntity<String> incompleteBooking(@RequestBody Booking booking){
        String status = agentService.incompleteBooking(booking);
        return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
    }
}
