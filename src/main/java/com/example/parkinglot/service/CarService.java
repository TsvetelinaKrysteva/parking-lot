package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
         List<CarDto> cars = ((List<Car>) carRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return cars;
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
        ParkingPlace parkingPlace = null;
        Car car = convertToCar(carDto);
        if (carRepository.findByPlateNumber(carDto.getPlateNumber()).isPresent()){
            throw new RuntimeException("Cars with same plate numbers can't exist!");
        }
        if(car.getParkingPlace()!=null){
            parkingPlace = parkingPlaceRepository.findById(carDto.getParkingPlaceDto().getId()).orElseThrow();

            if(parkingPlace.getCar() != null){
                throw new RuntimeException("The place is already taken!");
            }

            parkingPlace.setCar(car);
        }


        car.setParkingPlace(parkingPlace);
        carRepository.save(car);

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
        ParkingPlaceDto parkingPlaceDto = null;
        ParkingZoneDto parkingZoneDto = null;
        if(car.getParkingPlace()!=null){
            ParkingPlace parkingPlace = parkingPlaceRepository.findById(car.getParkingPlace().getId()).orElseThrow();
            parkingPlaceDto = new ParkingPlaceDto();
            parkingPlaceDto.setId(parkingPlace.getId());
            parkingPlaceDto.setNumber(Integer.toString(parkingPlace.getNumber()));
            ParkingZone parkingZone = parkingPlace.getParkingZone();
            parkingZoneDto = new ParkingZoneDto();
            parkingZoneDto.setId(parkingZone.getId());
            parkingZoneDto.setName(parkingZone.getName());
        }

        return new CarDto(car.getPlateNumber(), parkingPlaceDto, parkingZoneDto, car.getId());
    }

    public Car convertToCar(CarDto carDto){
        Car car = new Car();
        ParkingPlace parkingPlace = null;
        if (carDto.getParkingPlaceDto()!=null){
            parkingPlace = parkingPlaceRepository.findById(carDto.getParkingPlaceDto().getId()).orElseThrow();
        }

        if(carDto.getId() != null){
            car = carRepository.findById(carDto.getId()).orElseThrow();
        }

        car.setPlateNumber(carDto.getPlateNumber());
        car.setParkingPlace(parkingPlace);
//        parkingPlace.setCar(car);
        return car;

    }

    public void truncateCarTable(){
        carRepository.deleteAll();
    }

}
