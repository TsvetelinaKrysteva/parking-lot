package com.example.parkinglot.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;

@Service
public class CarService {


    @Autowired
    CarRepository carRepository;

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    @Autowired
    ParkingZoneRepository parkingZoneRepository;



    public List<CarDto> findByFilter(CarFilterDto filterDto){
        return carRepository.findByFilter(filterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CarDto> getAllCars() {
        return ((List<Car>) carRepository.findAll())
                .stream()
                .filter(c->c.getParkingPlace()!=null)
                // TODO: 26.1.2023 г. delete car without place
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

    public void createCar(CarDto carDto){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(carDto.getParkingPlaceDto().getId()).orElseThrow();
        Car car = convertToCar(carDto);
        if (carRepository.findByPlateNumber(carDto.getPlateNumber()).isPresent()){
            throw new RuntimeException("Cars with same plate numbers can't exist!");
        }
        if(parkingPlace.getCar() != null){
            throw new RuntimeException("The place is already taken!");
        }
        car.setParkingPlace(parkingPlace);
        carRepository.save(car);
        // TODO: 24.1.2023 г. parking place doesn't set the car; multiple cars reference to one place
        parkingPlace.setCar(car);
    }

    public void updateCar(CarDto carDto){


        Car car = convertToCar(carDto);
        carRepository.save(car);
    }

    public void deleteCar(Long id){

        getCar(id).getParkingPlace().setCar(null);
        carRepository.deleteById(id);
    }
    public CarDto convertToDto(Car car){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(car.getParkingPlace().getId()).orElseThrow();
        ParkingZone parkingZone = parkingPlace.getParkingZone();

        ParkingZoneDto parkingZoneDto = new ParkingZoneDto();
        parkingZoneDto.setId(parkingZone.getId());
        parkingZoneDto.setName(parkingZone.getName());

        ParkingPlaceDto parkingPlaceDto = new ParkingPlaceDto();
        parkingPlaceDto.setId(parkingPlace.getId());
        parkingPlaceDto.setNumber(Integer.toString(parkingPlace.getNumber()));


        return new CarDto(car.getPlateNumber(), parkingPlaceDto, parkingZoneDto, car.getId());
    }

    public Car convertToCar(CarDto carDto){
        Car car;

        ParkingPlace parkingPlace = parkingPlaceRepository.findById(carDto.getParkingPlaceDto().getId()).orElseThrow();

        if(carDto.getId() != null){
            car = carRepository.findById(carDto.getId()).orElseThrow();
        } else{
            car = new Car ();
        }
        car.setPlateNumber(carDto.getPlateNumber());
        car.setParkingPlace(parkingPlace);
        return car;

    }



}
