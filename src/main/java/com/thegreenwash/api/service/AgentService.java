package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.AgentNotFoundException;
import com.thegreenwash.api.exception.ComplexNotFoundException;
import com.thegreenwash.api.model.Agent;
import com.thegreenwash.api.model.Booking;
import com.thegreenwash.api.repository.AgentRepo;
import com.thegreenwash.api.repository.ComplexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AgentService {
    public final AgentRepo agentRepo;
    public final ComplexRepo complexRepo;
    public final BookingService bookingService;

    @Autowired
    public AgentService(AgentRepo agentRepo, ComplexRepo complexRepo, BookingService bookingService) {
        this.agentRepo = agentRepo;
        this.complexRepo = complexRepo;
        this.bookingService = bookingService;
    }

    public String addAgent(Agent agent){
        Agent temp = agentRepo.findByCellNum(agent.getCellNum());
        if(!Objects.isNull(temp)) {
            return "Agent already exists!";
        }
        else{
            complexRepo.findByComplexName(agent.getComplexName()).orElseThrow(
                    ()-> new ComplexNotFoundException("Not Found!"))
                    .getAgents().add(agent.getAgentId());
            agentRepo.save(agent);
            return "Success";
        }
    }
    public String updateAgent(Agent agent){
        agentRepo.save(agent);
        return "Agent updated successfully!";
    }

    public String login(String cellNum, String password){
        agentRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new AgentNotFoundException("Invalid Cell number or Password"));
        return "Success!";
    }

    public List<Booking> viewBookings(String agentId){
        return bookingService.agentViewBookings(agentId);
    }

    public String disableAgent(String agentId) {
        return "Agent disabled!";
    }

    public Agent findById(String agentId){
        return agentRepo.findById(agentId).orElseThrow(()-> new AgentNotFoundException("Agent does not exist!"));
    }
    public List<Agent> findAll(){
        return agentRepo.findAll();
    }

    public String incompleteBooking(Booking booking){
        return bookingService.inCompleteBooking(booking);
    }

    public String completeBooking(String bookingId){
        return bookingService.completeBooking(bookingId);
    }
}
