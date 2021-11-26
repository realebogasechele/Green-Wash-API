package com.thegreenwashapi.controller;

import com.thegreenwashapi.model.*;
import com.thegreenwashapi.service.ClientService;
import com.thegreenwashapi.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
        String response = clientService.addClient(client);
        if(response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/login/{cellNum}/{password}")
    public ResponseEntity<Client> login(@PathVariable("cellNum") String cellNum,
                                        @PathVariable("password") String password){
        Client client = clientService.login(cellNum,password);
        if(!Objects.isNull(client)){
            return new ResponseEntity<>(client, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(client, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client){
        Client status = clientService.updateClient(client);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/remove/{clientId}")
    public ResponseEntity<String> removeClient(@PathVariable("clientId") String clientId){
        String status = clientService.deleteClient(clientId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<Client> getClientDetails(@PathVariable("clientId") String clientId){
        Client client = clientService.getClientDetails(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/verify/cell/{cellNum}")
    public ResponseEntity<String> verifyCellNum(@PathVariable("cellNum") String cellNum){
        String response = clientService.verifyCellNumber(cellNum);
        if(response.equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/verify/email/{email}")
    public ResponseEntity<String> verifyEmail (@PathVariable("email") String email){
        String response = clientService.verifyEmail(email);
        if(response.equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
    }

    //Account Recovery
    @GetMapping("/recovery/otp/{username}")
    public ResponseEntity<String> recoveryOtp(@PathVariable("username") String username){
        String response = clientService.recoverSendOtp(username);
        if(response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recovery/recover/{otpNumber}/{username}")
    public ResponseEntity<String> recoverAccount(@PathVariable("otpNumber") Integer otpNumber,
                                                 @PathVariable("username") String username,
                                                 @PathVariable("time") String time){
        String response = clientService.recoverAccount(username, otpNumber, time);
        if(response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //Forgotten Password
    @PostMapping("forgot/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Client client){
        String response = clientService.changePassword(client);
        if(response.equals("error")){
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
        if(status == "Success") {
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("booking/viewBookings/{clientId}")
    public ResponseEntity<List<Booking>> viewBookings (@PathVariable ("clientId") String clientId){
        List<Booking> bookings = clientService.viewBookings(clientId);
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }

    @GetMapping("booking/getSuggestedTimes/{complexName}/{date}")
    public ResponseEntity<List<String>> getSuggestedTimes(@PathVariable("complexName") String complexName,
                                                              @PathVariable("date") String date){
        List<String> times = clientService.getSuggestedTimes(complexName, date);
        if(times.isEmpty()){
            return new ResponseEntity<>(times, HttpStatus.NOT_ACCEPTABLE);
        }else {
            return new ResponseEntity<>(times, HttpStatus.ACCEPTED);
        }
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

    //Complex Related
    @GetMapping("/complex/get/all")
    public ResponseEntity<List<Complex>> getComplexes(){
        List<Complex> response = clientService.getComplexes();
        if(response.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
