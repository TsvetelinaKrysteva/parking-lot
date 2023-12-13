package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import com.example.parkinglot.service.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;
    @Autowired
    CarRepository carRepository;


    @Transactional
    public List<ParkingDto> findByFilter(ParkingFilterDto filterDto){
        return parkingRepository.findByFilter(filterDto)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

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

    @Transactional
    public Parking getParkingByCarId(Long id){
        ParkingPlace parkingPlace = parkingPlaceRepository.findByCarId(id).orElseThrow(() -> new RuntimeException("Parking place doesn't exist!"));
        return parkingRepository.findByZonesId(parkingPlace.getParkingZone().getId()).orElseThrow(() -> new RuntimeException("Parking doesn't exist!"));
    }

    @Transactional
    public void createParking(ParkingDto parkingDto){
        Parking parking = convertToParking(parkingDto);
        if (parkingRepository.findByName(parking.getName()).isPresent()){
            throw new RuntimeException("Parkings with the same name can't exist!");
        }
        parkingRepository.save(parking);
    }

    @Transactional
    public void updateParking(ParkingDto parkingdto, Long parkingDtoId){
        parkingdto.setId(parkingDtoId);
        Parking parking = convertToParking(parkingdto);
        parkingRepository.save(parking);
    }

    @Transactional
    public void deleteParking(Long id){
        Parking parking = parkingRepository.findById(id).orElseThrow();
        if(!parking.getZones().isEmpty()){
            for(ParkingZone zone : parking.getZones()){
                if (!zone.getParkingPlaces().isEmpty()){
                    for (ParkingPlace place : zone.getParkingPlaces()){
                        if (place.getCar()!= null){
                            Car car = place.getCar();
                            car.setParkingPlace(null);
                            carRepository.save(car);
                        }
                    }
                }
            }

        }
        parkingRepository.deleteById(id);

    }

    public ParkingDto convertToDTO(Parking parking){
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setName(parking.getName());
        parkingDto.setId(parking.getId());
        parkingDto.setCity(parking.getCity());
        parkingDto.setStreet(parking.getStreet());
        parkingDto.setZipCode(parking.getZipCode());

        return parkingDto;
    }

    public Parking convertToParking(ParkingDto parkingDto){
        Parking parking;
        if (parkingDto.getId() != null){
            parking = parkingRepository.findById(parkingDto.getId()).orElseThrow();
        }else{
            parking = new Parking();
        }

        parking.setName(parkingDto.getName());
        parking.setCity(parkingDto.getCity());
        parking.setStreet(parkingDto.getStreet());
        parking.setZipCode(parkingDto.getZipCode());
        return parking;
    }

    public void truncateParkingTable(){
        parkingRepository.deleteAll();
    }
}
