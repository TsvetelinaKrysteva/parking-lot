package com.example.parkinglot.service;

import com.example.parkinglot.model.entity.Parking;

import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    @Autowired
    ParkingRepository parkingRepository;

    public List<Parking> getParkings(){
        return (List<Parking>) parkingRepository.findAll();
    }
    public Parking getParkingById(Long id){
        return parkingRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    public List<ParkingZone> getZonesByParkingId(Long id){
        return getParkingById(id).getZones();
    }
    public void createParking(Parking parking){

        for(ParkingZone zone:parking.getZones()){
            zone.setParking(parking);
        }

        parkingRepository.save(parking);
    }

    public void updateParking(Parking parking){
        parkingRepository.save(parking);
    }

    public void deleteParking(Long id){
        parkingRepository.deleteById(id);
    }

}
