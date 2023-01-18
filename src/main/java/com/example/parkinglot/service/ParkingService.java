package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingSecondDto;
import com.example.parkinglot.model.entity.Parking;

import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import com.example.parkinglot.service.repository.ParkingRepository;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    ParkingZoneRepository parkingZoneRepository;

    @Transactional
    public List<ParkingDto> getParkings(){

        return ((List<Parking>) parkingRepository.findAll())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Parking getParking(Long id){
        return parkingRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    public ParkingDto getParkingById(Long id){
        return convertToDTO(parkingRepository.findById(id).orElseThrow(() -> new RuntimeException("Parking doesn't exist!")));
    }

    public Parking getParkingByCarId(Long id){
        ParkingPlace parkingPlace = parkingPlaceRepository.findByCarId(id).orElseThrow(() -> new RuntimeException("Parking place doesn't exist!"));
        return parkingRepository.findByZonesId(parkingPlace.getParkingZone().getId()).orElseThrow(() -> new RuntimeException("Parking doesn't exist!"));
    }

    public void createParking(Parking parking){

        if (parkingRepository.findByName(parking.getName()).isPresent()){
            throw new RuntimeException("Parkings with the same name can't exist!");
        }
        parkingRepository.save(parking);
    }

    public void updateParking(Parking parking){
        parkingRepository.save(parking);
    }

    public void deleteParking(Long id){
        parkingRepository.deleteById(id);
    }

    public ParkingDto convertToDTO(Parking parking){
        return new ParkingDto(parking.getName(), parking.getCity(), parking.getStreet(), parking.getZipCode(), parking.getId());
    }

}
