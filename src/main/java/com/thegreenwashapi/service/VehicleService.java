package com.thegreenwashapi.service;

import com.thegreenwashapi.exception.VehicleNotFoundException;
import com.thegreenwashapi.model.Vehicle;
import com.thegreenwashapi.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepo vehicleRepo;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public String addVehicle(Vehicle vehicle){
        vehicleRepo.save(vehicle);
        return "Vehicle saved!";
    }

    public String updateVehicle(Vehicle vehicle){
        vehicleRepo.save(vehicle);
        return "Vehicle updated!";
    }

    public List<Vehicle> getAllVehicles(String clientId){
        return vehicleRepo.findAllByClientId(clientId).orElseThrow(()-> new VehicleNotFoundException("No vehicles!"));
    }

    public String deleteVehicle(String vehicleId) {
        vehicleRepo.deleteById(vehicleId);
        return "Vehicle removed";
    }
}
