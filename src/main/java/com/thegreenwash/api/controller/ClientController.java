package com.thegreenwash.api.controller;

import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client")
public class ClientController {
    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //Client Related
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Client client){
        String status = clientService.addClient(client);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/login/{cellNum}/{password}")
    public ResponseEntity<Client> login(@PathVariable("cellNum") String cellNum,
                                        @PathVariable("password") String password){
        Client client = clientService.login(cellNum,password);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateClient(@RequestBody Client client){
        String status = clientService.updateClient(client);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<Client> getClientDetails(@PathVariable("clientId") String clientId){
        Client client = clientService.getClientDetails(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/verify/cell/{cellNum}")
    public ResponseEntity<String> verifyCellNum(@PathVariable("cellNum") String cellNum){
        String status = clientService.verifyCellNumber(cellNum);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/verify/email/{email}")
    public ResponseEntity<String> verifyEmail (@PathVariable("email") String email){
        String status = clientService.verifyEmail(email);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    //Forgotten Password
    @PostMapping("forgot/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Client client){
        String status = clientService.changePassword(client);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    //Otp Related
    @PostMapping("/sendOtp/{cellNum}")
    public ResponseEntity<String> sendCellOtp (@PathVariable("cellNum") String cellNum){
       String status = clientService.sendCellOtp(cellNum);
       return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sendOtp/email/{email}")
    public ResponseEntity<String> sendEmailOtp (@PathVariable("email") String email){
        String status = clientService.sendEmailOtp(email);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/verifyOtp/{otpNumber}/{time}/{id}")
    public ResponseEntity<String> verifyOtp(@PathVariable("otpNumber") Integer otpNumber,
                                            @PathVariable("time") String time,
                                            @PathVariable("id") String id){
        String response = clientService.verifyOtp(otpNumber, time, id);
        if(response == "OTP ran out of time"){
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }else if(response == "Invalid OTP"){
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/resendOtp/cell/{cellNum}")
    public void resendCellOtp(@PathVariable("cellNum") String cellNum){
        clientService.resendCellOtp(cellNum);
    }

    @PostMapping("/resendOtp/email/{email}")
    public void resendEmailOtp(@PathVariable("email") String email){
        clientService.resendEmailOtp(email);
    }

    //Booking Related
    @PostMapping("booking/add")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking){
        String status = clientService.addBooking(booking);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("booking/viewBookings/{clientId}")
    public ResponseEntity<List<Booking>> viewBookings (@PathVariable ("clientId") String clientId){
        List<Booking> bookings = clientService.viewBookings(clientId);
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }

    @GetMapping("booking/getSuggestedTimes/{complexName}/{packageId}/{date}")
    public ResponseEntity<List<String>> getSuggestedTimes(@PathVariable("complexName") String complexName,
                                                              @PathVariable("packageId") String packageId,
                                                              @PathVariable("date") String date){
        List<String> times = clientService.getSuggestedTimes(complexName,packageId,date);
        return new ResponseEntity<>(times, HttpStatus.ACCEPTED);
    }

    @GetMapping("booking/getAgentDetails/{bookingId}")
    public ResponseEntity<Agent> getAgentDetails(@PathVariable("bookingId") String bookingId){
        Agent agent = clientService.getAgentDetails(bookingId);
        return new ResponseEntity<>(agent, HttpStatus.ACCEPTED);
    }

    //Package Related
    @GetMapping("/getPackages")
    public ResponseEntity<List<Package>> getPackages(){
        List<Package> packages = clientService.getPackages();
        return new ResponseEntity<>(packages, HttpStatus.ACCEPTED);
    }

    //Vehicle Related
    @PostMapping("/vehicle/add")
    public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle){
        String status = clientService.addVehicle(vehicle);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/vehicle/update")
    public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicle){
        String status = clientService.updateVehicle(vehicle);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/vehicle/delete/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("vehicleId") String vehicleId){
        String status = clientService.deleteVehicle(vehicleId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/vehicle/getAllVehicles/{clientId}")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@PathVariable("clientId") String clientId){
        List<Vehicle> vehicles = clientService.getAllVehicles(clientId);
        return new ResponseEntity<>(vehicles, HttpStatus.ACCEPTED);
    }
}
