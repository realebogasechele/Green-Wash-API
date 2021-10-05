package com.thegreenwash.api.service;

import com.thegreenwash.api.model.Packages;
import com.thegreenwash.api.repository.PackagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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





}
