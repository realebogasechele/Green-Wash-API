package com.thegreenwash.api.controller;

import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        String status = adminService.addAdmin(admin);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin) {
        String status = adminService.updateAdmin(admin);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/login/cell/{cellNum}/{password}")
    public ResponseEntity<String> cellLogin (@PathVariable("cellNum") String cellNum,
                                             @PathVariable("password") String password) {
        String adminId = adminService.cellLogin(cellNum, password);
        return new ResponseEntity<>(adminId, HttpStatus.ACCEPTED);
    }
    @GetMapping("/login/email/{email}/{password}")
    public ResponseEntity<String> emailLogin (@PathVariable("email") String email,
                                              @PathVariable("password") String password) {
        String adminId = adminService.emailLogin(email, password);
        return new ResponseEntity<>(adminId, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{adminId}")
    public ResponseEntity<Admin> getAdminDetails(@PathVariable("adminId") String adminId) {
        Admin admin = adminService.getAdminDetails(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    //Forgotten Password Related
    @PostMapping("/forgot/verify/cell/{cellNum}")
    public ResponseEntity<String> verifyCellNum(@PathVariable("cellNum") String cellNum) {
        String response = adminService.verifyCellNum(cellNum);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot/verify/email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable("email") String email) {
        String response = adminService.verifyEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot/verifyOtp/{otpNumber}/{time}/{id}")
    public ResponseEntity<String> verifyOtp(@PathVariable("otpNumber") Integer otpNumber,
                                            @PathVariable("time") String time,
                                            @PathVariable("id") String id) {
        String response = adminService.verifyOtp(otpNumber, time, id);
        if(response.equals("OTP ran out of time")){
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }else if(response.equals("Invalid OTP")){
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/forgot/resendOtp/cell/{cellNum}")
    public void resendCellOtp(@PathVariable("cellNum") String cellNum){
        adminService.resendCellOtp(cellNum);
    }

    @PostMapping("/forgot/resendOtp/email/{email}")
    public void resendEmailOtp(@PathVariable("email") String email){
        adminService.resendEmailOtp(email);
    }

    @PostMapping("/forgot/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Admin admin){
        String response = adminService.changePassword(admin);
        if(response.equals("Password Changed.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
    }

    //Client Related
    @GetMapping("/client/get/all")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> response = adminService.getClients();
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Booking Related
    @GetMapping("/booking/viewBookings")
    public ResponseEntity<List<Booking>> viewBookings() {
        List<Booking> bookings = adminService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }

    @GetMapping("/booking/get/{date}")
    public ResponseEntity<List<Booking>> viewBookings(@PathVariable("date") String date) {
        List<Booking> bookings = adminService.getBookings(date);
        return new ResponseEntity<>(bookings, HttpStatus.ACCEPTED);
    }

    @GetMapping("/booking/get/{pageNo}/{pageSize}/{sortBy}")
    public ResponseEntity<Map<String, Object>> getSortedBookings(@PathVariable("pageNo") int pageNo,
                                                                 @PathVariable("pageSize") int pageSize,
                                                                 @PathVariable("sortBy") String sortBy) {
        Map<String, Object> response = adminService.getSortedBookings(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Agent Related
    @PostMapping("/agent/add")
    public ResponseEntity<String> addAgent(@RequestBody Agent agent) {
        String status = adminService.addAgent(agent);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/agent/update")
    public ResponseEntity<String> updateAgent(@RequestBody Agent agent) {
        String status = adminService.updateAgent(agent);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/agent/disable/{agentId}")
    public ResponseEntity<String> disableAgent(@PathVariable("agentId") String agentId) {
        String status = adminService.disableAgent(agentId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/agent/get/{agentId}")
    public ResponseEntity<Agent> getAgent(@PathVariable("agentId") String agentId) {
        Agent agent = adminService.findByAgentId(agentId);
        return new ResponseEntity<>(agent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/agent/get/all")
    public ResponseEntity<List<Agent>> getAllAgent() {
        List<Agent> agents = adminService.getAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.ACCEPTED);
    }

    //Complex Related
    @PostMapping("/complex/add")
    public ResponseEntity<String> addComplex(@RequestBody Complex complex) {
        String status = adminService.addComplex(complex);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/complex/update")
    public ResponseEntity<String> updateComplex(@RequestBody Complex complex) {
        String status = adminService.updateComplex(complex);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/complex/remove/{complexId}")
    public ResponseEntity<String> removeComplex(@PathVariable("complexId") String complexId) {
        String status = adminService.removeComplex(complexId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/complex/get/all")
    public ResponseEntity<List<Complex>> getComplexes() {
        List<Complex> complexes = adminService.getComplexes();
        return new ResponseEntity<>(complexes, HttpStatus.ACCEPTED);
    }

    @GetMapping("/complex/get/{complexId}")
    public ResponseEntity<Complex> getComplexes(@PathVariable("complexId") String complexId) {
        Complex complex = adminService.findByComplexId(complexId);
        return new ResponseEntity<>(complex, HttpStatus.ACCEPTED);
    }

    //Package Related
    @PostMapping("/package/add")
    public ResponseEntity<String> addPackage(@RequestBody Package pack) {
        String status = adminService.addPackage(pack);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/package/update")
    public ResponseEntity<String> updatePackage(@RequestBody Package pack) {
        String status = adminService.updatePackage(pack);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/package/remove/{packageId}")
    public ResponseEntity<String> removePackage(@PathVariable("packageId") String packageId) {
        String status = adminService.removePackage(packageId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/package/get/{packageId}")
    public ResponseEntity<Package> findPackageById(@PathVariable("packageId") String packageId) {
        Package pack = adminService.findByPackageId(packageId);
        return new ResponseEntity<>(pack, HttpStatus.ACCEPTED);
    }

    @GetMapping("/package/get/all")
    public ResponseEntity<List<Package>> getPackages() {
        List<Package> packages = adminService.getPackages();
        return new ResponseEntity<>(packages, HttpStatus.ACCEPTED);
    }

    //Promotion Related
    @PostMapping("/promotion/add")
    public ResponseEntity<String> addPromotion(@RequestBody Promotion promotion) {
        String status = adminService.addPromotion(promotion);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/promotion/update")
    public ResponseEntity<String> updatePromotion(@RequestBody Promotion promotion) {
        String status = adminService.updatePromotion(promotion);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/promotion/remove/{promotionId}")
    public ResponseEntity<String> removePromotion(@PathVariable("promotionId") String promotionId) {
        String status = adminService.removePromotion(promotionId);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/promotion/get/all")
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = adminService.getPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.ACCEPTED);
    }

    @GetMapping("/promotion/get/{promotionId}")
    public ResponseEntity<Promotion> getPromotions(@PathVariable("promotionId") String promotionId) {
        Promotion promotion = adminService.findByPromotionId(promotionId);
        return new ResponseEntity<>(promotion, HttpStatus.ACCEPTED);
    }

    //Queries
    @GetMapping("/query/past7Days")
    public ResponseEntity<List<ResponseObject>> findBookingsForPast7Days() {
        List<ResponseObject> response = adminService.findAllForPast7Days();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query/packagePopularity")
    public ResponseEntity<List<ResponseObject>> packagePopularityInAComplex() {
        List<ResponseObject> response = adminService.packagePopularity();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query/clientPopulation")
    public ResponseEntity<List<ResponseObject>> clientPopulationInAComplex() {
        List<ResponseObject> response = adminService.clientPopulation();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query/complexMostBookings")
    public ResponseEntity<String> complexWithMostBookings() {
        String response = adminService.complexWithTheMostBookings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query/mostPopularPackage")
    public ResponseEntity<String> mostPopularPackage() {
        String response = adminService.mostPopularPackage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //This endpoint's information is redundant thanks to clientPopulation
    @Deprecated
    @GetMapping("/query/mostPopulatedComplex")
    public ResponseEntity<String> mostPopulatedComplex() {
        String response = adminService.mostPopulatedComplex();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
