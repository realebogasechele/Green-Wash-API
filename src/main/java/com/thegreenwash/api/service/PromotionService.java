package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.PackageNotFoundException;
import com.thegreenwash.api.exception.PromotionNotFoundException;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.model.Promotion;
import com.thegreenwash.api.repository.PackageRepo;
import com.thegreenwash.api.repository.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PromotionService {
    private final PromotionRepo promotionRepo;
    private final PackageRepo packageRepo;

    @Autowired
    public PromotionService(PromotionRepo promotionRepo, PackageRepo packageRepo) {
        this.promotionRepo = promotionRepo;
        this.packageRepo = packageRepo;
    }
    public Promotion findById(String promotionId){
        return promotionRepo.findById(promotionId).orElseThrow(()-> new PromotionNotFoundException("Not Found!"));
    }


    public String updatePromotion(Promotion promotion){
        if(!promotion.isEnabled()){
            try {
                Package pack = packageRepo.findByPackageName(promotion.getPackageName())
                        .orElseThrow(() -> new PackageNotFoundException("Not Found!"));
                pack.setOnPromotion(false);
                packageRepo.save(pack);
            }catch (PackageNotFoundException p){
                return "Package Not found!";
            }
        }
        promotionRepo.save(promotion);
        return "Success!";
    }

    public List<Promotion> getPromotions(){
        return promotionRepo.findAll();
    }

   public String addPromotion(Promotion promotion){
        try {
            Promotion temp = promotionRepo.findByPackageName(promotion.getPackageName());

            if (!Objects.isNull(temp)) {
                return "Promotion exists";
            } else {
                if (promotion.isEnabled()) {
                    //Set onPromotion to true on the package to disable it from being found in packages
                    Package pack = packageRepo.findByPackageName(promotion.getPackageName())
                            .orElseThrow(() -> new PackageNotFoundException("Not Found!"));
                    pack.setOnPromotion(true);
                    packageRepo.save(pack);
                }
                promotionRepo.save(promotion);
                return ("Success!");
            }
        }catch (PackageNotFoundException e){
            return "Package Not Found!";
        }
    }

    public String removePromotion(String promotionId){
        promotionRepo.deleteById(promotionId);
        return "Success";

    }


}