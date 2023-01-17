package com.example.parkinglot.controller;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class ParkingController {
    @Autowired
    ParkingService parkingService;
    @Autowired
    CarService carService;

    @GetMapping("/parkings")
    public List<ParkingDto> showParkings() {
        return parkingService.getParkings();
    }

    @GetMapping("/car-parking/{id}")
    public Parking showParkingOfTheCar(@PathVariable Long id){
        return carService.getParkingOfTheCar(id);
    }

    @GetMapping("/parking/{id}")
    public Parking showParkingById(@PathVariable Long id) {
        return parkingService.getParkingById(id);
    }


    @PostMapping("/create-parking")
    public void createParking(@RequestBody Parking parking) {
        parkingService.createParking(parking);
    }

    @PutMapping("/update-parking/{id}")
    public void updateParking(@RequestBody Parking parking, @PathVariable Long id) {

        parking.setId(id);
        parkingService.updateParking(parking);

    }

    @DeleteMapping("/delete-parking/{id}")
    public void deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
    }
}
