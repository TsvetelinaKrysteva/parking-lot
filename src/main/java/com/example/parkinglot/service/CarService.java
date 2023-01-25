package com.example.parkinglot.service;

import java.util.List;
import java.util.stream.Collectors;

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



    public List<CarDto> findByFilter(CarFilterDto filterDto){
        return carRepository.findByFilter(filterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CarDto> getAllCars() {
        return ((List<Car>) carRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Car getCar(Long id){
//        return carRepositoryImpl.findById(id).orElseThrow(() -> new RuntimeException("This car doesn't exist in the parking lot!"));

        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("This car doesn't exist in the parking lot!"));
    }
    public CarDto getCarById(Long id){
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Car> criteria = builder.createQuery(Car.class);
//        Root<Car> root = criteria.from(Car.class);
//        criteria.where(builder.equal(root.get("id"), id));
//
//        TypedQuery<Car> result = entityManager.createQuery(criteria);
//        CarDto match = convertToDto(result.getSingleResult());
//        return match;

        Car car = getCar(id);
        return this.convertToDto(car);
    }

    public CarDto getCarByPlaceId(Long id){
//        CriteriaBuilder builder  = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Car> criteria = builder.createQuery(Car.class);
//        Root<Car> root = criteria.from(Car.class);
//        criteria.where(builder.equal(root.get("parkingPlace").get("id"), id));
//
//        TypedQuery<Car> result = entityManager.createQuery(criteria);
//        CarDto match = convertToDto(result.getSingleResult());
//        return match;
        return convertToDto(carRepository.findByParkingPlaceId(id).orElseThrow( () -> new RuntimeException("This place is empty!")));
    }

    public void createCar(CarDto carDto, Long id){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(id).orElseThrow();
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

    public void updateCar(CarDto carDto, Long carDtoId){
        carDto.setId(carDtoId);
        Car car = convertToCar(carDto);
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

    public Car convertToCar(CarDto carDto){
        Car car;
        if(carDto.getId() != null){
            car = carRepository.findById(carDto.getId()).orElseThrow();
        } else{
            car = new Car ();
        }
        car.setPlateNumber(carDto.getPlateNumber());
        return car;

    }



}
