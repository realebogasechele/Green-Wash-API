package com.thegreenwash.api.controller;

import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //Admin Related
    @PostMapping("/signUp")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin){
        String status = adminService.addAdmin(admin);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin){
        String status = adminService.updateAdmin(admin);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/login/{cellNum}/{password}")
    public ResponseEntity<String> login(@PathVariable("cellNum") String cellNum,
                                        @PathVariable("password") String password){
        String status = adminService.login(cellNum,password);
        return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
    }

    //Booking Related
    @GetMapping("/booking/viewBookings")
    public ResponseEntity<List<Booking>> viewBookings(){
        List<Booking> bookings = adminService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }
    //Agent Related
    @PostMapping("/agents/add")
    public ResponseEntity<String> addAgent(@RequestBody Agent agent){
        String status = adminService.addAgent(agent);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/agents/update")
    public ResponseEntity<String> updateAgent(@RequestBody Agent agent){
        String status = adminService.updateAgent(agent);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/agents/disable/{agentId}")
    public ResponseEntity<String> disableAgent(@PathVariable("agentId") String agentId){
        String status = adminService.disableAgent(agentId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }
    //Complex Related
    @PostMapping("/complex/add")
    public ResponseEntity<String> addComplex(@RequestBody Complex complex){
        String status = adminService.addComplex(complex);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/complex/update")
    public ResponseEntity<String> updateComplex(@RequestBody Complex complex){
        String status = adminService.updateComplex(complex);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/complex/remove/{complexId}")
    public ResponseEntity<String> removeComplex(@PathVariable("complexId") String complexId){
        String status = adminService.removeComplex(complexId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/complex/getAll")
    public ResponseEntity<List<Complex>> getComplexes(){
        List<Complex> complexes = adminService.getComplexes();
        return new ResponseEntity<>(complexes, HttpStatus.ACCEPTED);
    }

    //Package Related
    @PostMapping("/package/add")
    public ResponseEntity<String> addPackage(@RequestBody Package pack){
        String status = adminService.addPackage(pack);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/package/update")
    public ResponseEntity<String> updatePackage(@RequestBody Package pack){
        String status = adminService.updatePackage(pack);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/package/remove/{packageId}")
    public ResponseEntity<String> removePackage(@PathVariable("packageId") String packageId){
        String status = adminService.removePackage(packageId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/package/getAll")
    public ResponseEntity<List<Package>> getPackages(){
        List<Package> packages = adminService.getPackages();
        return new ResponseEntity<>(packages, HttpStatus.ACCEPTED);
    }
    //Promotion Related
    @PostMapping("/promotion/add")
    public ResponseEntity<String> addPromotion(@RequestBody Promotion promotion){
        String status = adminService.addPromotion(promotion);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/promotion/update")
    public ResponseEntity<String> updatePromotion(@RequestBody Promotion promotion){
        String status = adminService.updatePromotion(promotion);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/promotion/remove/{packageId}")
    public ResponseEntity<String> removePromotion(@PathVariable("packageId") String promotionId){
        String status = adminService.removePromotion(promotionId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/promotion/getAll")
    public ResponseEntity<List<Promotion>> getPromotions(){
        List<Promotion> promotions = adminService.getPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.ACCEPTED);
    }

}
