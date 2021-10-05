package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.ClientNotFoundException;
import com.thegreenwash.api.model.Booking;
import com.thegreenwash.api.model.Client;
import com.thegreenwash.api.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepo clientRepo;
    private final OtpService otpService;
    private final BookingService bookingService;

    @Autowired
    public ClientService(ClientRepo clientRepo, OtpService otpService, BookingService bookingService) {
        this.clientRepo = clientRepo;
        this.otpService = otpService;
        this.bookingService = bookingService;
    }

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

    public String login(String cellNum, String password){
        clientRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new ClientNotFoundException("Invalid cell number or password"));
        return "Success!";
    }

    public void updateClient(Client client){
        clientRepo.save(client);
    }

    public List<Booking> viewBookings(String clientId){
        return bookingService.clientViewBookings(clientId);
    }

    public String addBooking(Booking booking){
        return bookingService.addBooking(booking);
    }

    public Client findByCellNum(String cellNum){
        return clientRepo.findByCellNum(cellNum);
    }

    public List<LocalTime> getSugestedTimes(String complexId, String packageId, LocalDate date){
        return bookingService.getSuggestedTimes(complexId, packageId, date);
    }

    public String sendOtp(String cellNum){
        try{
            otpService.send(cellNum);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "OTP successfully sent";
    }

    public void deleteClient(String clientId){
        //deactivate client
    }
}
