package com.example.parkinglot.controller;

import com.example.parkinglot.model.dto.ParkingPlaceDto;

import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingZoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingPlaceController {
    @Autowired
    ParkingPlaceService parkingPlaceService;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    CarService carService;

    @GetMapping("/car-parking-place/{id}")
    public ParkingPlaceDto showParkingPlaceOfTheCar(@PathVariable Long id){
        return parkingPlaceService.findByCarId(id);
    }
    @GetMapping("/places")
    public List<ParkingPlaceDto> findAllPlaces(){
        return parkingPlaceService.getParkingPlaces();
    }

    @GetMapping("/place/{id}")
    public ParkingPlaceDto showPlaceById(@PathVariable Long id) {
        return parkingPlaceService.getParkingPlaceById(id);
    }

    @GetMapping("/places-in-zone/{id}")
    public List<ParkingPlaceDto> showPlacesInParkingZone(@PathVariable Long id){
        return parkingPlaceService.getParkingPLacesByZoneId(id);
    }


    @PostMapping("/create-place/{parkingZoneId}")
    public void createParkingPlace(@Valid @RequestBody ParkingPlace parkingPlace, @PathVariable Long parkingZoneId){
        parkingPlaceService.createParkingPlace(parkingPlace, parkingZoneId);
    }

    @PutMapping("/update-place/{id}")
    public void updatePlace(@RequestBody ParkingPlace parkingPlace, @PathVariable Long id) {
        parkingPlace.setId(id);
        parkingPlace.setParkingZone(parkingPlaceService.getPlace(id).getParkingZone());

        parkingPlaceService.updateParkingPlace(parkingPlace);
    }

    @DeleteMapping("/delete-place/{id}")
    public void deletePlace(@PathVariable Long id) {
        parkingPlaceService.getPlace(id).getParkingZone().removePlace(parkingPlaceService.getPlace(id));
        parkingPlaceService.deleteParkingPlace(id);

    }
}
