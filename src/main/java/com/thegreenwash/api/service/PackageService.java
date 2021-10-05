package com.thegreenwash.api.service;


import com.thegreenwash.api.exception.PackageNotFoundException;
import com.thegreenwash.api.model.Package;
import com.thegreenwash.api.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepo packageRepo;


    @Autowired
    public PackageService(PackageRepo packageRepo) {
        this.packageRepo = packageRepo;
    }

    //Returns all the packages that aren't on Promotion
    public List<Package> getPackages(){
        return packageRepo.findAllByOnPromotion(false);
    }


    public void addPackage(Package pack){
        pack.setOnPromotion(false);
        packageRepo.save(pack);
    }

}
