package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingPlaceService {
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    ParkingZoneService parkingZoneService;

    public List<ParkingPlaceDto> getParkingPlaces(){
        return ((List<ParkingPlace>) parkingPlaceRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ParkingPlace getParkingPlaceById(Long id){
        return parkingPlaceRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Car getCarByPlaceId(Long id){
        return getParkingPlaceById(id).getCar();
    }
    public void createParkingPlace(ParkingPlace parkingPlace, Long parkingZoneId){
        ParkingZone parkingZone = parkingZoneService.getParkingZoneById(parkingZoneId);
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

    public ParkingPlaceDto convertToDto(ParkingPlace parkingPLace){
        ParkingZone parkingZone = parkingPLace.getParkingZone();
        ParkingZoneDto parkingZoneDto = new ParkingZoneDto(parkingZone.getName(), parkingZone.getParkingPlaces(), parkingZone.getId());
        return new ParkingPlaceDto(parkingPLace.getNumber(), parkingZoneDto, parkingPLace.getId());
    }

}
