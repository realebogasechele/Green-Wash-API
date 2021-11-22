package com.thegreenwashapi.service;

import com.thegreenwashapi.exception.ComplexNotFoundException;
import com.thegreenwashapi.model.Complex;
import com.thegreenwashapi.repository.ComplexRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ComplexService {
    private final ComplexRepo complexRepo;

    @Autowired
    public ComplexService(ComplexRepo complexRepo) {
        this.complexRepo = complexRepo;
    }

    public String addComplex(Complex complex){
        OffsetDateTime startTime = OffsetDateTime.parse(complex.getStartTime());
        OffsetDateTime endTime = OffsetDateTime.parse(complex.getEndTime());
        startTime = startTime.withMinute(0)
                .withSecond(0)
                .withNano(0);
        endTime = endTime
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        complex.setStartTime(startTime.toString());
        complex.setEndTime(endTime.toString());
        complexRepo.save(complex);
        return "Complex added!";
    }

    public String updateComplex(Complex complex){
        OffsetDateTime startTime = OffsetDateTime.parse(complex.getStartTime());
        OffsetDateTime endTime = OffsetDateTime.parse(complex.getEndTime());
        startTime = startTime.withMinute(0)
                .withSecond(0)
                .withNano(0);
        endTime = endTime
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        complex.setStartTime(startTime.toString());
        complex.setEndTime(endTime.toString());
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

    public List<String> getAllComplexNames() {
        List<Complex> complexes = complexRepo.findAll();
        List<String> complexNames = new ArrayList<>();

        for (Complex complex: complexes) {
            complexNames.add(complex.getComplexName());
        }

        return complexNames;
    }

    @Deprecated
    private List<String> fillComplexNames(@NotNull Stack<Complex> complexes, List<String> complexNames){
        if(complexes.isEmpty()){
            return complexNames;
        }else{
            complexNames.add(complexes.pop().getComplexName());
            return fillComplexNames(complexes, complexNames);
        }
    }
}
