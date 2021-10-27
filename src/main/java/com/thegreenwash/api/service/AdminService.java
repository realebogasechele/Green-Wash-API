package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.AdminNotFoundException;
import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepo adminRepo;
    private final AgentService agentService;
    private final BookingService bookingService;
    private final ComplexService complexService;
    private final PackageService packageService;
    private final PromotionService promotionService;

    @Autowired
    public AdminService(AdminRepo adminRepo, AgentService agentService, BookingService bookingService,
                        ComplexService complexService, PackageService packageService,
                        PromotionService promotionService) {
        this.adminRepo = adminRepo;
        this.agentService = agentService;
        this.bookingService = bookingService;
        this.complexService = complexService;
        this.packageService = packageService;
        this.promotionService = promotionService;
    }

    public String addAdmin(Admin admin){
        adminRepo.save(admin);
        return "Account created successfully!";
    }

    public String updateAdmin(Admin admin){
        adminRepo.save(admin);
        return "Account updated successfully!";
    }

    public String login(String cellNum, String password){
        adminRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new AdminNotFoundException("Invalid cellphone number or password!"));
        return "Success!";
    }

    //Agent Related
    public String addAgent(Agent agent){
        return agentService.addAgent(agent);
    }

    public String updateAgent(Agent agent){
        return agentService.updateAgent(agent);
    }

    public String disableAgent(String agentId){
        return agentService.disableAgent(agentId);
    }

    public List<Agent> getAllAgents(){ return agentService.findAll();}

    public Agent findByAgentId(String agentId) {
        return agentService.findById(agentId);
    }

    //Complex Related
    public String addComplex(Complex complex){
       return complexService.addComplex(complex);
    }

    public String updateComplex(Complex complex){
        return complexService.updateComplex(complex);
    }

    public String removeComplex(String complexId){
        return complexService.deleteComplex(complexId);
    }

    public Complex findByComplexId(String complexId){
        return complexService.findById(complexId);
    }

    public List<Complex> getComplexes(){
        return complexService.getAllComplexes();
    }

    //Package Related
    public String addPackage(Package pack){
        return packageService.addPackage(pack);
    }

    public String updatePackage(Package pack){
        return packageService.updatePackage(pack);
    }

    public Package findByPackageId(String packageId){
        return packageService.findById(packageId);
    }

    public List<Package> getPackages(){
        return packageService.getPackages();
    }

    public String removePackage(String packageId) {
        return packageService.removePackage(packageId);
    }

    //Promotion Related
    public String addPromotion(Promotion promotion){
        return promotionService.addPromotion(promotion);
    }

    public String updatePromotion(Promotion promotion){
        return promotionService.updatePromotion(promotion);
    }

    public String removePromotion(String promotionId){
        return promotionService.removePromotion(promotionId);
    }

    public Promotion findByPromotionId(String promotionId){ return promotionService.findById(promotionId);}

    public List<Promotion> getPromotions(){
        return promotionService.getPromotions();
    }

    //Booking Related
    public List<Booking> getAllBookings(){
        return bookingService.adminViewBookings();
    }
    public List<Booking> getBookings(String date){return bookingService.getBookings(date);}


}
