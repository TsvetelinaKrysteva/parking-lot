package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.model.dto.UserFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.model.entity.User;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarService carService;

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public List<UserDto> getAllUsers(){
        return ((List<User>) userRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public UserDto getUserById(Long id){
        return this.convertToDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("This user doesn't exist!")));
    }

    public List<UserDto> findByFilter(UserFilterDto filterDto){
        return userRepository.findByFilter(filterDto)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void createUser(UserDto userDto){
        User user = convertToUser(userDto);
        userRepository.save(user);
        if(!userDto.getCar().isEmpty()){
            for (Car car: user.getCar()){
                car.setUser(user);
            }
        }

    }
    @Transactional
    public void updateUser(UserDto userDto){
//        if(userDto.getCar()!=null){
//
//        }
        User user = convertToUser(userDto);

        List<Car> cars = carRepository.findByUserId(userDto.getId());
        Set<Long> carIds = userDto.getCar().stream().map(CarDto::getId).collect(Collectors.toSet());
        for(Car car : cars){
            if(carIds.contains(car.getId())) {
                continue;
            }
            car.setUser(null);
            carRepository.save(car);
        }
        userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDto convertToDto(User user){
        UserDto userDto= new UserDto();
        Set<CarDto> carDto = Collections.emptySet();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        if(!user.getCar().isEmpty()){
            carDto = user.getCar().stream().map(c -> carService.convertToDto(c)).collect(Collectors.toSet());
        }
        return new UserDto(user.getId(), user.getName(), carDto);

    }
    public User convertToUser(UserDto userDto){

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        if(!userDto.getCar().isEmpty()){
            user.setCar(new ArrayList<>());
            for (CarDto cars : userDto.getCar()){
                Car car = new Car();
                car.setId(cars.getId());
                user.addCar(car);
            }
        }
        return user;
    }
}
