package com.example.parkinglot.service;

import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.repository.CarRepository;
import com.example.parkinglot.repository.ParkingPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public Car getCarById(Long id){
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public ParkingPlace getParkingPlaceOfTheCar(Long id){
        return getCarById(id).getParkingPlace();
    }

    public ParkingZone getParkingZoneOfTheCar(Long id){
        return getParkingPlaceOfTheCar(id).getParkingZone();
    }

     public Parking getParkingOfTheCar(Long id){
        return getParkingZoneOfTheCar(id).getParking();
    }
    public void createCar(Car car){
        car.getParkingPlace().setCar(car);
        carRepository.save(car);

    }
    public void updateCar(Car car){

        carRepository.save(car);
    }

    public void deleteCar(Long id){
        getCarById(id).getParkingPlace().setCar(null);
        carRepository.deleteById(id);
    }


}
