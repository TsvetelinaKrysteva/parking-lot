package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;
//    @Autowired
//    ParkingPlaceRepository parkingPlaceRepository;

    public List<CarDto> getAllCars() {
        return ((List<Car>) carRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Car getCar(Long id){
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    public CarDto getCarById(Long id){
        Car car = getCar(id);
        return this.convertToDto(car);
    }

    public ParkingPlace getParkingPlaceOfTheCar(Long id){
        return getCar(id).getParkingPlace();
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
//        carRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found")).getParkingPlace().setCar(null);
        getCar(id).getParkingPlace().setCar(null);
        carRepository.deleteById(id);
    }
    public CarDto convertToDto(Car car){
        ParkingPlace parkingPlace = car.getParkingPlace();
        return new CarDto(car.getPlateNumber(), parkingPlace.getNumber(), parkingPlace.getParkingZone().getName(), car.getId());
    }


}
