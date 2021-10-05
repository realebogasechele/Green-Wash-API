package com.thegreenwash.api.service;


import com.thegreenwash.api.model.Packages;
import com.thegreenwash.api.repository.PackagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PackagesService {

    private final PackagesRepo packagesRepo;


    @Autowired
    public PackagesService(PackagesRepo packagesRepo) {
        this.packagesRepo = packagesRepo;
    }

    //Returns all the packages
    public List<Packages> getPackages(){
        return packagesRepo.findAll();
    }


    public void addPackage(Packages packages){
        Optional<Packages> packagesOptional =
                packagesRepo.findByPackageID(packages.getPackageID());

        if(packagesOptional.isPresent()){
            throw new IllegalStateException("Package exists");
        }else {
            packagesRepo.save(packages);
        }

    }




}
