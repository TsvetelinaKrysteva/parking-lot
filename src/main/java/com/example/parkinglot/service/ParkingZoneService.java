package com.example.parkinglot.service;

import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ParkingZoneService {
    @Autowired
    ParkingZoneRepository parkingZoneRepository;
    public List<ParkingZone> getParkingZones(){
        return (List<ParkingZone>) parkingZoneRepository.findAll();
    }
    public ParkingZone getParkingZoneById(Long id){
        return parkingZoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<ParkingPlace> getParkingPlacesByZoneId(Long id){
        return getParkingZoneById(id).getParkingPlaces();
    }

    public void createParkingZone(ParkingZone parkingZone, Parking parking){
        parking.addNewZone(parkingZone);
        parkingZone.setParking(parking);
        parkingZoneRepository.save(parkingZone);
    }
    public void updateParkingZone(ParkingZone parkingZone){

        parkingZoneRepository.save(parkingZone);

    }

    public void deleteParkingZone(Long id){

        parkingZoneRepository.deleteById(id);
    }

}
