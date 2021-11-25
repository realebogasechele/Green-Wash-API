package com.thegreenwashapi.service;

import com.thegreenwashapi.config.PBKDF2Hasher;
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
    public final PBKDF2Hasher hasher;
    public final ComplexRepo complexRepo;
    public final BookingService bookingService;

    @Autowired
    public AgentService(AgentRepo agentRepo, PBKDF2Hasher hasher, ComplexRepo complexRepo, BookingService bookingService) {
        this.agentRepo = agentRepo;
        this.hasher = hasher;
        this.complexRepo = complexRepo;
        this.bookingService = bookingService;
    }

    public String addAgent(Agent agent) {
        Agent temp = agentRepo.findByCellNum(agent.getCellNum());
        if (!Objects.isNull(temp)) {
            return "Agent already exists!";
        } else {
            try {
                Complex complex = complexRepo.findByComplexName(agent.getComplexName()).orElseThrow(
                        () -> new ComplexNotFoundException("Not Found!"));

                complex.getAgents().add(agent.getSurname());
                complexRepo.save(complex);
                agent.setPassword(hasher.hash(agent.getPassword().toCharArray()));
                agent.setDisabled(false);
                agentRepo.save(agent);
                return "success";

            } catch (Exception ex) {
                ex.printStackTrace();
                return "Incorrect Entered Complex";
            }
        }
    }

    public String updateAgent(Agent agent) {
        agentRepo.save(agent);
        return "Agent updated successfully!";
    }

    public String login(String cellNum, String password) {
        Agent agent = agentRepo.findByCellNumAndIsDisabled(cellNum, false);
        boolean check = hasher.checkPassword(password.toCharArray(), agent.getPassword());
        if(check){
            return agent.getAgentId();
        }else{
            return "error";
        }
    }

    public Agent findById(String agentId) {
        Agent agent = agentRepo.findByAgentIdAndIsDisabled(agentId, false)
                .orElseThrow(() -> new AgentNotFoundException("Not Found!"));
        agent.setPassword("");
        return agent;
    }

    public List<Booking> viewBookings(String agentId) {
        return bookingService.agentViewBookings(agentId);
    }

    public String disableAgent(String agentId) {
        try {
            Agent agent = agentRepo.findById(agentId).orElseThrow(() -> new AgentNotFoundException("Not Found."));
            agent.setDisabled(true);
            agentRepo.save(agent);
            return "success";
        }catch (Exception ex){
            return "error";
        }
    }

    @Deprecated
    public Agent findBySurname(String agentSurname) {
        return agentRepo.findBySurname(agentSurname);
    }

    public List<Agent> findAll() {
        List<Agent> agents = agentRepo.findAllByIsDisabled(false);
        for (Agent agent: agents) {
            agent.setPassword("");
        }
        return agents;
    }

    public String incompleteBooking(Booking booking) {
        return bookingService.inCompleteBooking(booking);
    }

    public String completeBooking(String bookingId) {
        return bookingService.completeBooking(bookingId);
    }
}
