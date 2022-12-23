package com.example.parkinglot.service;

import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.repository.ParkingPlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingPlaceService {
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;


    public List<ParkingPlace> getParkingPlaces(){
        return (List<ParkingPlace>) parkingPlaceRepository.findAll();
    }
    public ParkingPlace getParkingPlaceById(Long id){
        return parkingPlaceRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Car getCarByPlaceId(Long id){
        return getParkingPlaceById(id).getCar();
    }
    public void createParkingPlace(ParkingPlace parkingPlace, ParkingZone parkingZone){
        parkingZone.addNewPlace(parkingPlace);
        parkingPlace.setParkingZone(parkingZone);
        parkingPlaceRepository.save(parkingPlace);
    }
    public void updateParkingPlace(ParkingPlace parkingPlace){
        parkingPlaceRepository.save(parkingPlace);
    }

    public void deleteParkingPlace(Long id){

        parkingPlaceRepository.deleteById(id);
    }

}
