package com.thegreenwash.api.service;

import com.thegreenwash.api.model.Client;
import com.thegreenwash.api.model.Packages;
import com.thegreenwash.api.model.Promotion;
import com.thegreenwash.api.model.Vehicle;
import com.thegreenwash.api.repository.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Service
public class PromotionService {
    private final PromotionRepo promRepo;

    @Autowired
    public PromotionService(PromotionRepo promRepo) {
        this.promRepo = promRepo;
    }


    /*public void updatePromotion(Promotion prom){
        PromotionRepo.save(prom);
    }
    * */

    public List<Promotion> getPromotions(){
        return promRepo.findAll();
    }

   /* public String addPromotion(Promotion prom){

        Promotion temp = PromotionRepo.findByIsEnabledAndPackageId(prom.isEnabled(),prom.getPackageId());

        if(!Objects.isNull(temp)) {
            return "Promotion exists";
        }
        else{
            PromotionRepo.save(prom);
            return("Success!");
        }
    }*/


}