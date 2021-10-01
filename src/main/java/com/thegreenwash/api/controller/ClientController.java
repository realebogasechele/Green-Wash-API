package com.thegreenwash.api.controller;

import com.thegreenwash.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client")
public class ClientController {
    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/sendOtp/{cellNum}")
    public ResponseEntity<String> sendOtp (@PathVariable("cellNum") String cellNum){
       String status = clientService.sendOtp(cellNum);
       return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }
}
