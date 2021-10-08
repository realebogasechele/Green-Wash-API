package com.thegreenwash.api.service;

import com.thegreenwash.api.model.Complex;
import com.thegreenwash.api.repository.ComplexRepo;

import java.util.List;

public class ComplexService {
    private final ComplexRepo complexRepo;

    public ComplexService(ComplexRepo complexRepo) {
        this.complexRepo = complexRepo;
    }

    public String addComplex(Complex complex){
        complexRepo.save(complex);
        return "Complex added!";
    }

    public String updateComplex(Complex complex){
        complexRepo.save(complex);
        return  "Complex updated!";
    }

    //Still under probation. More Research required about the deletion of Items
    public String deleteComplex(String complexId){
        complexRepo.deleteById(complexId);
        return "Complex removed!";
    }

    public List<Complex> getAllComplexes() {
        return complexRepo.findAll();
    }
}
