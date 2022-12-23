package com.example.parkinglot.controller;

import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    @Autowired
    CarService carService;
    @Autowired
    ParkingPlaceService parkingPlaceService;

    @GetMapping("/cars")
    public List<Car> findAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/car/{id}")
    public Car showCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @GetMapping("/car-parking-place/{id}")
    public ParkingPlace showParkingPlaceOfTheCar(@PathVariable Long id){
        return carService.getParkingPlaceOfTheCar(id);
    }

    @GetMapping("/car-parking-zone/{id}")
    public ParkingZone showParkingZoneOfTheCar(@PathVariable Long id){
        return carService.getParkingZoneOfTheCar(id);
    }

    @GetMapping("/car-parking/{id}")
    public Parking showParkingOfTheCar(@PathVariable Long id){
        return carService.getParkingOfTheCar(id);
    }

    @PostMapping("/create-car/{placeId}")
    public void createCar(@RequestBody Car car, @PathVariable Long placeId){
        if(parkingPlaceService.getCarByPlaceId(placeId) == null){
            car.setParkingPlace(parkingPlaceService.getParkingPlaceById(placeId));
            carService.createCar(car);
        }
    }

    @PutMapping("/update-car/{id}")
    public void updateCar(@RequestBody Car car, @PathVariable Long id) {
        car.setId(id);
        car.setParkingPlace(carService.getCarById(id).getParkingPlace());
        carService.updateCar(car);
    }

    @DeleteMapping("/delete-car/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
