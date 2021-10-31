package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.ComplexNotFoundException;
import com.thegreenwash.api.model.Complex;
import com.thegreenwash.api.repository.ComplexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ComplexService {
    private final ComplexRepo complexRepo;

    @Autowired
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

    public Complex findById(String complexId){
        return complexRepo.findById(complexId).orElseThrow(()->new ComplexNotFoundException("Not Found!"));
    }

    public List<Complex> getAllComplexes() {
        return complexRepo.findAll();
    }
}
