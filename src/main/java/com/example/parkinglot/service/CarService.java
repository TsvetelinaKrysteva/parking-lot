package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.model.entity.User;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.ParkingPlaceRepository;
import com.example.parkinglot.service.repository.ParkingZoneRepository;
import com.example.parkinglot.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    @Autowired
    UserRepository userRepository;



    @Transactional
    public List<CarDto> findByFilter(CarFilterDto filterDto){
        return carRepository.findByFilter(filterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
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

    @Transactional
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
        List<UserDto> userDtos = new ArrayList<>();
        if(car.getParkingPlace()!=null){
//            ParkingPlace parkingPlace = parkingPlaceRepository.findById(car.getParkingPlace().getId()).orElseThrow();
            ParkingPlace parkingPlace = car.getParkingPlace();
            parkingPlaceDto = new ParkingPlaceDto();
            parkingPlaceDto.setId(parkingPlace.getId());
            parkingPlaceDto.setNumber(Integer.toString(parkingPlace.getNumber()));
            ParkingZone parkingZone = parkingPlace.getParkingZone();
            parkingZoneDto = new ParkingZoneDto();
            parkingZoneDto.setId(parkingZone.getId());
            parkingZoneDto.setName(parkingZone.getName());
        }
        if(car.getUsers()!= null && !CollectionUtils.isEmpty(car.getUsers())){
            for(User user : car.getUsers()){
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDtos.add(userDto);
            }

        }

        return new CarDto(car.getPlateNumber(), parkingPlaceDto, parkingZoneDto, userDtos, car.getId());
    }

    public Car convertToCar(CarDto carDto){
        Car car = new Car();
        ParkingPlace parkingPlace = null;
        List<User> users = new ArrayList<>();
        if (carDto.getParkingPlaceDto()!=null){
//            parkingPlace = parkingPlaceRepository.findById(carDto.getParkingPlaceDto().getId()).orElseThrow();
            parkingPlace = new ParkingPlace();
            parkingPlace.setId(carDto.getParkingPlaceDto().getId());
        }
        if(carDto.getUsers()!= null && !CollectionUtils.isEmpty(carDto.getUsers())){
            for (UserDto userDto : carDto.getUsers()){
                User user = new User();
                user.setId(userDto.getId());
                users.add(user);
            }

        }
//        if(carDto.getId() != null){
//            car = carRepository.findById(carDto.getId()).orElseThrow();
//        }
        if(carDto.getId()!= null){
            car.setId(carDto.getId());
        }
        car.setPlateNumber(carDto.getPlateNumber());
        car.setParkingPlace(parkingPlace);
        car.setUsers(users);
//        parkingPlace.setCar(car);
        return car;

    }

    public void truncateCarTable(){
        carRepository.deleteAll();
    }

}
