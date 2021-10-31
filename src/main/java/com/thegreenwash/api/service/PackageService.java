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

    public Package findById(String packageId){
        return packageRepo.findById(packageId).orElseThrow(()-> new PackageNotFoundException("Not Found!"));
    }

    //Returns all the packages that aren't on Promotion
    public List<Package> getPackages(){
        return packageRepo.findAllByOnPromotion(false);
    }


    public String addPackage(Package pack){
        pack.setOnPromotion(false);
        packageRepo.save(pack);
        return "Package created!";
    }

    public String updatePackage(Package pack) {
        packageRepo.save(pack);
        return "Updated successfully!";
    }

    public String removePackage(String packageId) {
        packageRepo.deleteById(packageId);
        return "Success!";
    }

}
