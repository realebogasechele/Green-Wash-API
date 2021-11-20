package com.thegreenwashapi.service;

import com.thegreenwashapi.exception.AgentNotFoundException;
import com.thegreenwashapi.exception.ComplexNotFoundException;
import com.thegreenwashapi.model.Agent;
import com.thegreenwashapi.model.Booking;
import com.thegreenwashapi.model.Complex;
import com.thegreenwashapi.repository.AgentRepo;
import com.thegreenwashapi.repository.ComplexRepo;
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
            try {
                Complex complex =complexRepo.findByComplexName(agent.getComplexName()).orElseThrow(
                        () -> new ComplexNotFoundException("Not Found!"));

                complex.getAgents().add(agent.getSurname());
                complexRepo.save(complex);
                agentRepo.save(agent);
                return "Success";

            }catch (Exception ex){
                ex.printStackTrace();
                return "Incorrect Entered Complex";
            }
        }
    }
    public String updateAgent(Agent agent){
        agentRepo.save(agent);
        return "Agent updated successfully!";
    }

    public String login(String cellNum, String password){
        try {
            return agentRepo.findByCellNumAndPassword(cellNum, password)
                    .orElseThrow(() -> new AgentNotFoundException("Invalid Cell number or Password")).getAgentId();
        }catch (Exception ex){
            return "Invalid Cell number or Password!";
        }
    }
    public Agent findById(String agentId){
        return agentRepo.findById(agentId).orElseThrow(()-> new AgentNotFoundException("Not Found!"));
    }

    public List<Booking> viewBookings(String agentId){
        return bookingService.agentViewBookings(agentId);
    }

    public String disableAgent(String agentId) {
        return "Agent disabled!";
    }

    public Agent findBySurname(String agentSurname){
        return agentRepo.findBySurname(agentSurname);
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
