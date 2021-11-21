package com.thegreenwashapi.service;

import com.thegreenwashapi.config.PBKDF2Hasher;
import com.thegreenwashapi.exception.ClientNotFoundException;
import com.thegreenwashapi.exception.ComplexNotFoundException;
import com.thegreenwashapi.model.*;
import com.thegreenwashapi.repository.ClientRepo;
import com.thegreenwashapi.repository.ComplexRepo;
import com.thegreenwashapi.model.Package;
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
                Client temp2 = clientRepo.findByUnitNum(client.getUnitNum());

                if (!Objects.isNull(temp)) {
                    return "error";
                } else if (!Objects.isNull(temp2)) {
                    return "error";
                } else {
                    client.setPassword(hasher.hash(client.getPassword().toCharArray()));
                    clientRepo.save(client);
                    return ("Success!");
                }
            }
        } catch (ComplexNotFoundException e) {
            return "error";
        }
    }

    public String updateClient(Client client) {
        clientRepo.save(client);
        return "Success";
    }

    public void deleteClient(String clientId) {
        //deactivate client
    }

    public String verifyCellNumber(String cellNum) {
        Client client = clientRepo.findByCellNum(cellNum);
        if (!Objects.isNull(client)) {
            return client.getClientId();
        } else {
            return "error";
        }
    }

    public String verifyEmail(String email) {
        Client client = clientRepo.findByEmail(email);
        if (!Objects.isNull(client)) {
            return client.getClientId();
        } else {
            return "error";
        }
    }

    public Client login(String cellNum, String password) {
        Client client = clientRepo.findByCellNum(cellNum);
        boolean check = hasher.checkPassword(password.toCharArray(), client.getPassword());
        if (check) {
            client.setPassword("");
            return client;
        } else {
            return new Client();
        }
    }

    //Forgotten Password
    public String changePassword(Client client) {
        try {
            Client existingClient = clientRepo.findById(client.getClientId()).orElseThrow(() -> new ClientNotFoundException("Not Found."));
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
        return agentService.findById(bookingService.findById(bookingId).getAgentId());
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
