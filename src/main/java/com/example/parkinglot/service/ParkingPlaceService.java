package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
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


    public List<ParkingPlaceDto> findByFilter(ParkingPlaceFilterDto parkingPlaceFilterDto){
        return parkingPlaceRepository.findByFilter(parkingPlaceFilterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ParkingPlace getPlace(long id){
        return parkingPlaceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid place!"));
    }
    public List<ParkingPlaceDto> getParkingPlaces(){
        return ((List<ParkingPlace>) parkingPlaceRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ParkingPlaceDto getParkingPlaceById(Long id){
        return convertToDto(parkingPlaceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid place!")));
    }

    public ParkingPlaceDto findByCarId(Long carId){
        return convertToDto(parkingPlaceRepository.findByCarId(carId).orElseThrow( () -> new RuntimeException("Invalid place!")));
    }

    public List<ParkingPlaceDto> getParkingPLacesByZoneId(Long id){
        return parkingPlaceRepository.findByParkingZoneId(id)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public void createParkingPlace(ParkingPlace parkingPlace, Long parkingZoneId){
        ParkingZone parkingZone = parkingZoneService.getZone(parkingZoneId);
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
