package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.AgentNotFoundException;
import com.thegreenwash.api.model.Agent;
import com.thegreenwash.api.model.Booking;
import com.thegreenwash.api.repository.AgentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AgentService {
    public final AgentRepo agentRepo;
    public final BookingService bookingService;

    @Autowired
    public AgentService(AgentRepo agentRepo, BookingService bookingService) {
        this.agentRepo = agentRepo;
        this.bookingService = bookingService;
    }

    public String addAgent(Agent agent){

        if(agentRepo.existsById(agent.getAgentId())) {
            return "Agent already exists!";
        }
        else{
            agentRepo.save(agent);
            return "Success";
        }
    }

    public String login(String cellNum, String password){
        agentRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new AgentNotFoundException("Invalid Cell number or Password"));
        return "Success!";
    }

    public List<Booking> viewBookings(String agentId){
        return bookingService.agentViewBookings(agentId);
    }
}
