package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.AdminNotFoundException;
import com.thegreenwash.api.model.*;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.AdminRepo;
import com.thegreenwash.api.repository.BookingRepo;
import com.thegreenwash.api.repository.ComplexRepo;
import com.thegreenwash.api.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class AdminService {
    private final AdminRepo adminRepo;
    private final MongoTemplate mongoTemplate;
    private final ComplexRepo complexRepo;
    private final PackageRepo packageRepo;
    private final BookingRepo bookingRepo;
    private final AgentService agentService;
    private final BookingService bookingService;
    private final ComplexService complexService;
    private final PackageService packageService;
    private final PromotionService promotionService;

    @Autowired
    public AdminService(AdminRepo adminRepo, MongoTemplate mongoTemplate, ComplexRepo complexRepo, PackageRepo packageRepo, BookingRepo bookingRepo, AgentService agentService, BookingService bookingService,
                        ComplexService complexService, PackageService packageService,
                        PromotionService promotionService) {
        this.adminRepo = adminRepo;
        this.mongoTemplate = mongoTemplate;
        this.complexRepo = complexRepo;
        this.packageRepo = packageRepo;
        this.bookingRepo = bookingRepo;
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

    public Admin login(String cellNum, String password){
        return adminRepo.findByCellNumAndPassword(cellNum, password)
                .orElseThrow(()-> new AdminNotFoundException("Invalid cellphone number or password!"));

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


    public Admin getAdminDetails(String adminId) {
        return adminRepo.findById(adminId).orElseThrow(()-> new AdminNotFoundException("Does not exist!"));
    }

    public Map<String, Object> getSortedBookings(int pageNo, int pageSize, String sortBy) {
        return bookingService.getSortedBookings(pageNo, pageSize, sortBy);
    }

    //Queries
    public Map<String, Integer> findAllForPast7Days(){
        Map<String, Integer> response = new HashMap<>();
        OffsetDateTime dateTime = OffsetDateTime.now();
        List<Booking> bookings;

        for(long i = 0; i <= 7; i++) {
            bookings = bookingRepo.findAllByDate(dateTime.minusDays(i).toLocalDate().toString());
            response.put("Day " + i, bookings.size());
        }

        return response;
    }

    public Map<String, Integer> packagePopularity(){
        Map<String,Integer> response = new HashMap<>();
        List<Booking> bookings;
        List<Package> packages = packageRepo.findAll();

        for(Package pack: packages) {
            bookings = bookingRepo.findAllByPackageId(pack.getPackageId());
            response.put(pack.getPackageName(), bookings.size());
        }
        return response;
    }

    public Map<String, Integer> clientPopulation(){
        Query query = new Query();
        List<Complex> complexes = complexRepo.findAll();
        Map<String, Integer> response = new HashMap<>();

        for (Complex complex: complexes) {
            query.addCriteria(Criteria.where("complexId").is(complex.getComplexId()));
            List<Client> clients = mongoTemplate.find(query, Client.class);
            response.put(complex.getComplexName(), clients.size());
        }

        return response;
    }

    public String mostPopulatedComplex(){
        Query query = new Query();
        List<Complex> complexes = complexRepo.findAll();
        List<Client> clients;
        Map<String, Integer> numberOfClients = new HashMap<>();
        for(int i = 0; i <= complexes.size(); i++){
            query.addCriteria(Criteria.where("complexId").is(complexes.get(i).getComplexId()));
            clients = mongoTemplate.find(query, Client.class);
            numberOfClients.put(complexes.get(i).getComplexName(), clients.size());
        }
        numberOfClients = sortByValue(numberOfClients);

        List<String> keys = new ArrayList<>(numberOfClients.keySet());

        return keys.get(0);
    }

    public String complexWithTheMostBookings(){
        Query query = new Query();
        List<Complex> complexes = complexRepo.findAll();
        List<Booking> bookings;
        Map<String, Integer> numberOfBookings = new HashMap<>();
        for(Complex complex: complexes){
            query.addCriteria(Criteria.where("complexId").is(complex.getComplexId()));
            bookings = mongoTemplate.find(query, Booking.class);
            numberOfBookings.put(complex.getComplexName(), bookings.size());
        }
        numberOfBookings = sortByValue(numberOfBookings);
        List<String> keys = new ArrayList<>(numberOfBookings.keySet());
        return keys.get(0);
    }

    public String mostPopularPackage(){
        Query query = new Query();
        List<Package> packages = packageRepo.findAll();
        List<Booking> bookings;
        Map<String, Integer> numberOfPackages = new HashMap<>();
        for(Package pack: packages){
            bookings = bookingRepo.findAllByPackageId(pack.getPackageId());
            numberOfPackages.put(pack.getPackageName(), bookings.size());
        }

        numberOfPackages = sortByValue(numberOfPackages);
        List<String> keys = new ArrayList<>(numberOfPackages.keySet());
        return keys.get(0);
    }

    //Utility classes
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortedMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<>(unsortedMap.entrySet());

        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }
}
