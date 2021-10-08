package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.ClientNotFoundException;
import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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

    @Autowired
    public ClientService(ClientRepo clientRepo, PackageService packageService, OtpService otpService, BookingService bookingService, VehicleService vehicleService, AgentService agentService) {
        this.clientRepo = clientRepo;
        this.packageService = packageService;
        this.otpService = otpService;
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
        this.agentService = agentService;
    }
    //Client related
    public String addClient(Client client){
        Client temp = clientRepo.findByCellNumAndPasswordAndDeviceId(client.getCellNum(), client.getPassword(), client.getDeviceId());
        Client temp2 = clientRepo.findByCellNum(client.getCellNum());
        Client temp3 = clientRepo.findByDeviceId(client.getDeviceId());

        if(!Objects.isNull(temp) || !Objects.isNull(temp2) || !Objects.isNull(temp3)) {
            return "User exists";
        }
        else{
            clientRepo.save(client);
            return("Success!");
        }
    }

    public String updateClient(Client client){
        clientRepo.save(client);
        return "Success";
    }

    public void deleteClient(String clientId){
        //deactivate client
    }

    public String login(String cellNum, String password){
        clientRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new ClientNotFoundException("Invalid cell number or password"));
        return "Success!";
    }

    //Booking related
    public List<Booking> viewBookings(String clientId){
        return bookingService.clientViewBookings(clientId);
    }

    public String addBooking(Booking booking){
        return bookingService.addBooking(booking);
    }

    public List<LocalTime> getSuggestedTimes(String complexId, String packageId, LocalDate date){
        return bookingService.getSuggestedTimes(complexId, packageId, date);
    }

    public Agent getAgentDetails(String bookingId){
        return agentService.findById(bookingService.findById(bookingId).getAgentId());
    }
    
    //Otp Related
    public String sendOtp(String cellNum){
        try{
            otpService.send(cellNum);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "OTP successfully sent";
    }

    public String verifyOtp(Integer otpNumber, ZonedDateTime time){
        return otpService.verifyOtp(otpNumber, time);
    }

    public void resendOtp(String clientId){
        otpService.resendOtp(clientId);
    }

    //Vehicle Related
    public String addVehicle(Vehicle vehicle){
        return vehicleService.addVehicle(vehicle);
    }

    public String updateVehicle(Vehicle vehicle){
        return vehicleService.updateVehicle(vehicle);
    }

    public String deleteVehicle(String vehicleId){
        return vehicleService.deleteVehicle(vehicleId);
    }

    public List<Vehicle> getAllVehicles(String clientId){
        return vehicleService.getAllVehicles(clientId);
    }

    //Package Related
    public List<Package> getPackages(){
        return packageService.getPackages();
    }
}
