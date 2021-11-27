package com.thegreenwashapi.controller;

import com.thegreenwashapi.model.*;
import com.thegreenwashapi.service.AdminService;
import com.thegreenwashapi.model.Package;
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
        String response = adminService.addAdmin(admin);
        if (response.equals("success")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestBody Admin admin) {
        String response = adminService.updateAdmin(admin);
        if (response.equals("success")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/remove/{adminId}")
    public ResponseEntity<String> removeAdmin(@PathVariable("adminId") String adminId) {
        String response = adminService.removeAdmin(adminId);
        if (response.equals("success")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<String> unifiedLogin(@PathVariable("username") String username,
                                               @PathVariable("password") String password) {
        String adminId = adminService.unifiedLogin(username, password);
        if (!adminId.equals("error")) {
            return new ResponseEntity<>(adminId, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Invalid Cell Number or Password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{adminId}")
    public ResponseEntity<Admin> getAdminDetails(@PathVariable("adminId") String adminId) {
        Admin admin = adminService.getAdminDetails(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    //Account Recovery
    @GetMapping("/recovery/otp/{username}")
    public ResponseEntity<String> recoveryOtp(@PathVariable("username") String username) {
        String response = adminService.recoverSendOtp(username);
        if (!response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recovery/recover/{otpNumber}/{adminId}/{time}")
    public ResponseEntity<String> recoverAccount(@PathVariable("otpNumber") Integer otpNumber,
                                                 @PathVariable("adminId") String adminId,
                                                 @PathVariable("time") String time) {
        String response = adminService.recoverAccount(adminId, otpNumber, time);
        if (!response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recovery/resendOtp/{username}")
    public ResponseEntity<String> resendOtp(@PathVariable("username") String username) {
        String response = adminService.resendOtp(username);
        if (response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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
        if (response.equals("OTP ran out of time")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        } else if (response.equals("Invalid OTP")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/forgot/resendOtp/cell/{cellNum}")
    public ResponseEntity<String> resendCellOtp(@PathVariable("cellNum") String cellNum) {
        String response = adminService.resendCellOtp(cellNum);
        if (response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/forgot/resendOtp/email/{email}")
    public ResponseEntity<String> resendEmailOtp(@PathVariable("email") String email) {
        String response = adminService.resendEmailOtp(email);
        if (response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/forgot/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Admin admin) {
        String response = adminService.changePassword(admin);
        if (response.equals("success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    //Client Related
    @GetMapping("/client/get/all")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> response = adminService.getClients();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/client/remove/{clientId}")
    public ResponseEntity<String> removeClient(@PathVariable("clientId") String clientId) {
        String response = adminService.removeClient(clientId);
        if (!response.equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
        if (status == "success") {
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agent/update")
    public ResponseEntity<String> updateAgent(@RequestBody Agent agent) {
        String status = adminService.updateAgent(agent);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/agent/remove/{agentId}")
    public ResponseEntity<String> disableAgent(@PathVariable("agentId") String agentId) {
        String status = adminService.disableAgent(agentId);
        if (status == "success") {
            return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/agent/get/all/disabled")
    public ResponseEntity<List<Agent>> getAllDisabledAgent() {
        List<Agent> agents = adminService.getAllDisabledAgents();
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
        if (complexes.isEmpty()) {
            return new ResponseEntity<>(complexes, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(complexes, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/complex/get/{complexId}")
    public ResponseEntity<Complex> getComplexes(@PathVariable("complexId") String complexId) {
        Complex complex = adminService.findByComplexId(complexId);
        return new ResponseEntity<>(complex, HttpStatus.ACCEPTED);
    }

    @GetMapping("/complex/get/all/names")
    public ResponseEntity<List<String>> getComplexNames() {
        List<String> response = adminService.getAllComplexNames();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
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

    @GetMapping("/package/get/all/names")
    public ResponseEntity<List<String>> getPackageNames() {
        List<String> response = adminService.getPackageNames();
        if (response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
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

    @GetMapping("/query/typeOfBookingsToday")
    public ResponseEntity<List<String>> typeOfBookings() {
        List<String> response = adminService.typeOfBookingsToday();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query/totalEarningsToday")
    public ResponseEntity<List<String>> totalEarnings() {
        List<String> response = adminService.totalEarningsToday();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //This endpoint's information is redundant thanks to clientPopulation
    @Deprecated
    @GetMapping("/query/mostPopulatedComplex")
    public ResponseEntity<String> mostPopulatedComplex() {
        String response = adminService.mostPopulatedComplex();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Deprecated
    @GetMapping("/login/cell/{cellNum}/{password}")
    public ResponseEntity<String> cellLogin(@PathVariable("cellNum") String cellNum,
                                            @PathVariable("password") String password) {
        String adminId = adminService.cellLogin(cellNum, password);
        if (!adminId.equals("error")) {
            return new ResponseEntity<>(adminId, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(adminId, HttpStatus.BAD_REQUEST);
        }
    }

    @Deprecated
    @GetMapping("/login/email/{email}/{password}")
    public ResponseEntity<String> emailLogin(@PathVariable("email") String email,
                                             @PathVariable("password") String password) {
        String adminId = adminService.emailLogin(email, password);
        if (!adminId.equals("error")) {
            return new ResponseEntity<>(adminId, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(adminId, HttpStatus.BAD_REQUEST);
        }
    }
}