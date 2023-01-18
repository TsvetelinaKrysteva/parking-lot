package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.CarRepository;
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
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("This car doesn't exist in the parking lot!"));
    }
    public CarDto getCarById(Long id){
        Car car = getCar(id);
        return this.convertToDto(car);
    }

    public CarDto getCarByPlaceId(Long id){
        return convertToDto(carRepository.findByParkingPlaceId(id).orElseThrow( () -> new RuntimeException("This place is empty!")));
    }

    public void createCar(Car car){
        if (carRepository.findByPlateNumber(car.getPlateNumber()).isPresent()){
            throw new RuntimeException("Cars with same plate numbers can't exist!");
        }
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
