package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import com.example.parkinglot.service.repository.ParkingRepository;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingZoneService {
    @Autowired
    ParkingZoneRepository parkingZoneRepository;
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    ParkingPlaceService parkingPlaceService;
//    @Autowired
//    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingService parkingService;


    public List<ParkingZoneDto> filter(ParkingZoneFilterDto parkingZoneFilterDto){
        return parkingZoneRepository.findByFilter(parkingZoneFilterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional

    public List<ParkingZoneDto> getParkingZones() {
        return ((List<ParkingZone>) parkingZoneRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ParkingZone getZone(Long id){
        return parkingZoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Such zone doesn't exist!"));
    }
    public ParkingZoneDto getParkingZoneById(Long id) {
        return convertToDto(parkingZoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Such zone doesn't exist!")));
    }
    public List<ParkingZoneDto> getZonesByParkingId(Long id){
        return (List<ParkingZoneDto>) convertToDto(parkingZoneRepository.findByParkingId(id).orElseThrow( () -> new RuntimeException("Not found")));
    }

   public ParkingZone getZoneByCarId(Long id){
       ParkingPlace parkingPlace = parkingPlaceRepository.findByCarId(id).orElseThrow( () -> new RuntimeException("Such place doesn't exist!"));
       return parkingZoneRepository.findByParkingPlacesId(parkingPlace.getId()).orElseThrow( () -> new RuntimeException("Such zone doesn't exist!"));
   }

    @Transactional
    public void createParkingZone(ParkingZoneDto parkingZoneDto) {
        Parking parking = parkingRepository.findById(parkingZoneDto.getParkingDto().getId()).orElseThrow( () -> new RuntimeException("Such parking doesn't exist!"));
        ParkingZone parkingZone = convertToParkingZone(parkingZoneDto);
        if (parkingZoneRepository.findByName(parkingZone.getName()).isPresent()){
            throw new RuntimeException("Zones with the same name can't exist!");
        }
        parking.addNewZone(parkingZone);
        parkingZone.setParking(parking);
        parkingZoneRepository.save(parkingZone);
    }

    @Transactional
    public void updateParkingZone(ParkingZoneDto parkingZoneDto) {
//        parkingZoneDto.setId(parkingZoneDto.getId());
        ParkingZone parkingZone = convertToParkingZone(parkingZoneDto);
        Parking parking = parkingZone.getParking();

        if(!parking.getZones().contains(parkingZone)){
            parking.addNewZone(parkingZone);
        }
        parkingZoneRepository.save(parkingZone);

    }

    public void deleteParkingZone(Long id) {

        parkingZoneRepository.deleteById(id);
    }

    public ParkingZoneDto convertToDto(ParkingZone parkingZone){
        Parking parking = parkingZone.getParking();
        List<ParkingPlaceDto> places= (List<ParkingPlaceDto>) parkingZone.getParkingPlaces().stream().map(p -> parkingPlaceService.convertToDto(p)).collect(Collectors.toList());

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setId(parking.getId());
        parkingDto.setName(parking.getName());
        parkingDto.setCity(parking.getCity());
        parkingDto.setStreet(parkingDto.getStreet());
        return new ParkingZoneDto(parkingZone.getName(), places, parkingZone.getId(),parkingDto);

    }

    public ParkingZone convertToParkingZone(ParkingZoneDto parkingZoneDto){
        ParkingZone parkingZone;
        if(parkingZoneDto.getId() != null){
            parkingZone = parkingZoneRepository.findById(parkingZoneDto.getId()).orElseThrow();
        }else{
            parkingZone = new ParkingZone();
        }

        parkingZone.setName(parkingZoneDto.getName());
        parkingZone.setParking(parkingRepository.findById(parkingZoneDto.getParkingDto().getId()).orElseThrow());
        return parkingZone;
    }

}
