package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingZoneService {
    @Autowired
    ParkingZoneRepository parkingZoneRepository;
    @Autowired
    ParkingService parkingService;


    public List<ParkingZoneDto> getParkingZones() {
        return ((List<ParkingZone>) parkingZoneRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ParkingZone getParkingZoneById(Long id) {
        return parkingZoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<ParkingPlace> getParkingPlacesByZoneId(Long id) {
        return getParkingZoneById(id).getParkingPlaces();
    }

    public void createParkingZone(ParkingZone parkingZone, Long parkingId) {
        Parking parking = parkingService.getParkingById(parkingId);
        parking.addNewZone(parkingZone);
        parkingZone.setParking(parking);
        parkingZoneRepository.save(parkingZone);
    }

    public void updateParkingZone(ParkingZone parkingZone) {

        parkingZoneRepository.save(parkingZone);

    }

    public void deleteParkingZone(Long id) {

        parkingZoneRepository.deleteById(id);
    }

    public ParkingZoneDto convertToDto(ParkingZone parkingZone){
        return new ParkingZoneDto(parkingZone.getName(), parkingZone.getParkingPlaces(), parkingZone.getId());
    }

}
