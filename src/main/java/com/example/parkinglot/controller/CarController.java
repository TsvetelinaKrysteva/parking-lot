package com.example.parkinglot.controller;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.entity.Car;

import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import jakarta.validation.Valid;
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
    public List<CarDto> findAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/car-on-place/{id}")
    public CarDto showCarByParkingPlaceId(@PathVariable Long id){
        return carService.getCarByPlaceId(id);
    }

    @GetMapping("/car/{id}")
    public CarDto showCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping("/create-car/{placeId}")
    public void createCar(@Valid @RequestBody Car car, @PathVariable Long placeId){
        if(parkingPlaceService.getPlace(placeId).getCar() == null){
            car.setParkingPlace(parkingPlaceService.getPlace(placeId));
            carService.createCar(car);

        } else {
            throw new RuntimeException("The place is already taken!");
        }
    }

    @PutMapping("/update-car/{id}")
    public void updateCar(@RequestBody Car car, @PathVariable Long id) {
        car.setId(id);
        car.setParkingPlace(carService.getCar(id).getParkingPlace());
//                getCarById(id).getParkingPlace());
        carService.updateCar(car);
    }

    @DeleteMapping("/delete-car/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
