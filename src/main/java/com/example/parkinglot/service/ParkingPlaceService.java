package com.example.parkinglot.service;


import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;

import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;

import com.example.parkinglot.service.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingPlaceService {
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    ParkingZoneService parkingZoneService;

    @Autowired
    ParkingZoneRepository parkingZoneRepository;

    public List<ParkingPlaceDto> findByFilter(ParkingPlaceFilterDto parkingPlaceFilterDto){
        return parkingPlaceRepository.findByFilter(parkingPlaceFilterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ParkingPlace getPlace(long id){
        return parkingPlaceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid place!"));
    }

    @Transactional
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


    public void createParkingPlace(ParkingPlaceDto parkingPlaceDto){
        ParkingPlace parkingPlace = convertToParkingPlace(parkingPlaceDto);
        ParkingZone parkingZone = parkingZoneService.getZone(parkingPlaceDto.getParkingZone().getId());
        parkingZone.addNewPlace(parkingPlace);
        parkingPlace.setParkingZone(parkingZone);
        parkingPlaceRepository.save(parkingPlace);
    }
    public void updateParkingPlace(ParkingPlaceDto parkingPlaceDto){

        ParkingPlace parkingPlace = convertToParkingPlace(parkingPlaceDto);
        parkingPlaceRepository.save(parkingPlace);
    }

    public void deleteParkingPlace(Long id){

        parkingPlaceRepository.deleteById(id);
    }

    public ParkingPlaceDto convertToDto(ParkingPlace parkingPLace){
        ParkingZone parkingZone = parkingPLace.getParkingZone();
        Parking parking = parkingZone.getParking();

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setName(parking.getName());
        parkingDto.setId(parking.getId());
        parkingDto.setCity(parkingDto.getCity());
        parkingDto.setStreet(parkingDto.getStreet());
        parkingDto.setZipCode(parking.getZipCode());

        ParkingZoneDto parkingZoneDto = new ParkingZoneDto();
        parkingZoneDto.setId(parkingZone.getId());
        parkingZoneDto.setName(parkingZone.getName());
        parkingZoneDto.setParkingDto(parkingDto);

        return new ParkingPlaceDto(Integer.toString(parkingPLace.getNumber()), parkingZoneDto, parkingPLace.getId());
    }

    public ParkingPlace convertToParkingPlace(ParkingPlaceDto parkingPlaceDto){
        ParkingPlace parkingPlace;
        if (parkingPlaceDto.getId() != null){
            parkingPlace = parkingPlaceRepository.findById(parkingPlaceDto.getId()).orElseThrow();
        }
        else{
            parkingPlace = new ParkingPlace();
        }
        parkingPlace.setNumber(Integer.parseInt(parkingPlaceDto.getNumber()));
        parkingPlace.setParkingZone(parkingZoneRepository.findById(parkingPlaceDto.getParkingZone().getId()).orElseThrow());
        return parkingPlace;

    }

}
