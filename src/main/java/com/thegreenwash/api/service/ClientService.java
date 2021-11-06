package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.ClientNotFoundException;
import com.thegreenwash.api.exception.ComplexNotFoundException;
import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.ClientRepo;
import com.thegreenwash.api.repository.ComplexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
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
    private final ComplexRepo complexRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo, PackageService packageService,
                         OtpService otpService, BookingService bookingService,
                         VehicleService vehicleService, AgentService agentService,
                         ComplexRepo complexRepo) {
        this.clientRepo = clientRepo;
        this.packageService = packageService;
        this.otpService = otpService;
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
        this.agentService = agentService;
        this.complexRepo = complexRepo;
    }
    //Client related
    public String addClient(Client client){
        try {
            List<String> units = complexRepo.findByComplexName(client.getComplexName()).orElseThrow(
                    () -> new ComplexNotFoundException("Not Found!")).getUnits();
            boolean found = false;
            for (String unit: units) {
                if (unit == client.getUnitNum()) {
                    found = true;

                }
            }
            if (found == true) {
                return "Unit does not exist";
            } else {
                Client temp = clientRepo.findByCellNum(client.getCellNum());
                Client temp2 = clientRepo.findByUnitNum(client.getUnitNum());

                if (!Objects.isNull(temp)) {
                    return "User exists";
                } else if (!Objects.isNull(temp2)) {
                    return "An occupant of this unit number already exists!";
                } else {
                    clientRepo.save(client);
                    return ("Success!");
                }
            }
        }catch (ComplexNotFoundException e) {
            return "Complex Does Not Exist!";
        }
    }

    public String updateClient(Client client){
        clientRepo.save(client);
        return "Success";
    }

    public void deleteClient(String clientId){
        //deactivate client
    }
    public String verifyCellNumber(String cellNum){
        Client client = clientRepo.findByCellNum(cellNum);
        if(Objects.isNull(client)) {
            return "Verified";
        }else{
            return "User exists!";
        }
    }

    public Client login(String cellNum, String password){
        try {
            return clientRepo.findByCellNumAndPassword(cellNum, password)
                    .orElseThrow(() -> new ClientNotFoundException("Invalid cell number or password"));
        }catch (ClientNotFoundException e){
            Client emptyClient = new Client();
            return emptyClient;
        }
    }

    //Booking related
    public List<Booking> viewBookings(String clientId){
        return bookingService.clientViewBookings(clientId);
    }

    public String addBooking(Booking booking){
        return bookingService.addBooking(booking);
    }

    public List<OffsetTime> getSuggestedTimes(String complexId, String packageId, String date){
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

    public void resendOtp(String cellNum){
        otpService.resendOtp(cellNum);
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

    public Client getClientDetails(String clientId) {
        return clientRepo.findById(clientId).orElseThrow(()-> new ClientNotFoundException("Does not exist!"));
    }
}
