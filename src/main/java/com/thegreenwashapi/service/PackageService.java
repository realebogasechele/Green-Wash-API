package com.thegreenwashapi.service;


import com.thegreenwashapi.exception.PackageNotFoundException;
import com.thegreenwashapi.model.Package;
import com.thegreenwashapi.repository.PackageRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public List<String> getPackageNames() {
        List<Package> packages = packageRepo.findAll();
        List<String> packageNames = new ArrayList<>();

        for(Package pack: packages){
            packageNames.add(pack.getPackageName());
        }

        return packageNames;
    }

    @Deprecated
    private List<String> fillPackageNames(@NotNull Stack<Package> packages, List<String> packageNames){
        if(packages.isEmpty()){
            return packageNames;
        }else{
            packageNames.add(packages.pop().getPackageName());
            return fillPackageNames(packages, packageNames);
        }
    }
}
