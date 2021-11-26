package com.thegreenwashapi.service;

import com.thegreenwashapi.config.PBKDF2Hasher;
import com.thegreenwashapi.exception.ClientNotFoundException;
import com.thegreenwashapi.exception.ComplexNotFoundException;
import com.thegreenwashapi.model.*;
import com.thegreenwashapi.repository.ClientRepo;
import com.thegreenwashapi.repository.ComplexRepo;
import com.thegreenwashapi.model.Package;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepo clientRepo;
    private final PackageService packageService;
    private final OtpService otpService;
    private final BookingService bookingService;
    private final VehicleService vehicleService;
    private final AgentService agentService;
    private final ComplexRepo complexRepo;
    private final PBKDF2Hasher hasher;

    @Autowired
    public ClientService(ClientRepo clientRepo, PackageService packageService,
                         OtpService otpService, BookingService bookingService,
                         VehicleService vehicleService, AgentService agentService,
                         ComplexRepo complexRepo, PBKDF2Hasher hasher) {
        this.clientRepo = clientRepo;
        this.packageService = packageService;
        this.otpService = otpService;
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
        this.agentService = agentService;
        this.complexRepo = complexRepo;
        this.hasher = hasher;
    }

    //Client related
    public String addClient(Client client) {
        try {
            List<String> units = complexRepo.findByComplexName(client.getComplexName()).orElseThrow(
                    () -> new ComplexNotFoundException("Not Found!")).getUnits();
            boolean found = false;
            for (String unit : units) {
                if (unit.equals(client.getUnitNum())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return "error";
            } else {
                Client temp = clientRepo.findByCellNum(client.getCellNum());
                Client temp2 = clientRepo.findByEmail(client.getEmail());
                Client temp3 = clientRepo.findByUnitNum(client.getUnitNum());

                if (!Objects.isNull(temp)) {
                    return "error";
                } else if (!Objects.isNull(temp2)) {
                    return "error";
                }else if (!Objects.isNull(temp3)) {
                    return "error";
                } else {
                    client.setPassword(hasher.hash(client.getPassword().toCharArray()));
                    client.setCounter(0);
                    client.setDisabled(false);
                    client.setBlacklisted(false);
                    clientRepo.save(client);
                    return ("Success!");
                }
            }
        } catch (ComplexNotFoundException e) {
            return "error";
        }
    }

    public Client updateClient(Client client) {
        Client existingClient = clientRepo.findById(client.getClientId()).orElseThrow(()-> new ClientNotFoundException("Not Found."));
        client.setDisabled(existingClient.getDisabled());
        client.setBlacklisted(existingClient.getBlacklisted());
        client.setCounter(existingClient.getCounter());
        client.setPassword(hasher.hash(client.getPassword().toCharArray()));
        clientRepo.save(client);
        return client;
    }

    public String deleteClient(String clientId) {
        try {
            Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Not Found."));
            client.setDisabled(true);
            clientRepo.save(client);
            return "Client Profile Deleted.";
        }catch (Exception ex){
            return "error";
        }
    }

    public String verifyCellNumber(String cellNum) {
        Client client = clientRepo.findByCellNumAndIsDisabledAndIsBlacklisted(cellNum, false, false);
        if (!Objects.isNull(client)) {
            return client.getClientId();
        } else {
            return "error";
        }
    }

    public String verifyEmail(String email) {
        Client client = clientRepo.findByEmailAndIsDisabledAndIsBlacklisted(email, false, false);
        if (!Objects.isNull(client)) {
            return client.getClientId();
        } else {
            return "error";
        }
    }

    public String recoverSendOtp(String username){
        try {
            Client cellClient = clientRepo.findByCellNum(username);
            Client emailClient = clientRepo.findByEmail(username);

            if (!Objects.isNull(cellClient)) {
                otpService.sendClientCellOtp(username);
                return username;
            } else if (!Objects.isNull(emailClient)) {
                otpService.sendClientEmailOtp(username);
                return username;
            } else {
                return "error";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return "error";
        }
    }

    public String recoverAccount(String username, Integer otpNumber, String time){
        try {
            Client cellClient = clientRepo.findByCellNum(username);
            Client emailClient = clientRepo.findByEmail(username);

            if (!Objects.isNull(cellClient)) {
                String response = otpService.verifyOtp(otpNumber, time, username);
                if(response.equals(username)) {
                    cellClient.setDisabled(false);
                    clientRepo.save(cellClient);
                    return "Account Recovered.";
                }else{
                    return "error";
                }
            } else if (!Objects.isNull(emailClient)) {
                String response = otpService.verifyOtp(otpNumber, time, username);
                if(response.equals(username)) {
                    emailClient.setDisabled(false);
                    clientRepo.save(emailClient);
                    return "Account Recovered.";
                }else {
                    return "error";
                }
            } else {
                return "error";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return "error";
        }
    }

    public String permanentRemoval(String username){
        Client cellClient = clientRepo.findByCellNum(username);
        Client emailClient = clientRepo.findByEmail(username);

        if(!Objects.isNull(cellClient)){
            cellClient.setCellNum("");
            cellClient.setEmail("");
            cellClient.setPassword("");
            cellClient.setDisabled(true);
            clientRepo.save(cellClient);
            return "Your account has been permanently removed.";
        }else if(!Objects.isNull(emailClient)){
            emailClient.setCellNum("");
            emailClient.setEmail("");
            emailClient.setPassword("");
            emailClient.setDisabled(true);
            clientRepo.save(emailClient);
            return "Your account has been permanently removed.";
        }else{
            return "error";
        }
    }

    public Client login(String cellNum, @NotNull String password) {
        Client client = clientRepo.findByCellNumAndIsDisabledAndIsBlacklisted(cellNum, false, false);
        boolean check = hasher.checkPassword(password.toCharArray(), client.getPassword());
        if (check) {
            client.setPassword("");
            return client;
        } else {
            return null;
        }
    }

    //Forgotten Password
    public String changePassword(Client client) {
        try {
            Client existingClient = clientRepo.findByClientIdAndIsDisabled(client.getClientId(), false).orElseThrow(() -> new ClientNotFoundException("Not Found."));
            existingClient.setPassword(hasher.hash(client.getPassword().toCharArray()));
            clientRepo.save(existingClient);
            return "Password Changed.";
        } catch (Exception ex) {
            return "error";
        }
    }

    //Booking related
    public List<Booking> viewBookings(String clientId) {
        return bookingService.clientViewBookings(clientId);
    }

    public String addBooking(Booking booking) {
        return bookingService.addBooking(booking);
    }

    public List<String> getSuggestedTimes(String complexName, String date) {
        return bookingService.getSuggestedTimes(complexName, date);
    }

    public Agent getAgentDetails(String bookingId) {
        String agentId = bookingService.findById(bookingId).getAgentId();
        Agent agent = agentService.findById(agentId);
        agent.setPassword("");
        return agent;
    }

    //Otp Related
    public String sendCellOtp(String cellNum) {
        try {
            otpService.sendClientCellOtp(cellNum);
            return cellNum;
        } catch (Exception e) {
            e.printStackTrace();
            return "OTP not sent";
        }

    }

    public String sendEmailOtp(String email) {
        try {
            otpService.sendClientEmailOtp(email);
            return email;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "OTP not sent.";
        }

    }

    public String verifyOtp(Integer otpNumber, String time, String id) {
        return otpService.verifyOtp(otpNumber, time, id);
    }

    public void resendCellOtp(String cellNum) {
        otpService.resendClientCellOtp(cellNum);
    }

    public void resendEmailOtp(String email) {
        otpService.resendClientEmailOtp(email);
    }

    //Vehicle Related
    public String addVehicle(Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    public String updateVehicle(Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle);
    }

    public String deleteVehicle(String vehicleId) {
        return vehicleService.deleteVehicle(vehicleId);
    }

    public List<Vehicle> getAllVehicles(String clientId) {
        return vehicleService.getAllVehicles(clientId);
    }

    //Package Related
    public List<Package> getPackages() {
        return packageService.getPackages();
    }

    public Client getClientDetails(String clientId) {
        return clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Does not exist!"));
    }

    //Complex Related
    public List<Complex> getComplexes() {
        return complexRepo.findAll();
    }


}
