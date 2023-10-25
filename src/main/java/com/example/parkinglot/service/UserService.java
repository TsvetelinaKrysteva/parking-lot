package com.example.parkinglot.service;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.model.dto.UserFilterDto;
import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.User;
import com.example.parkinglot.service.repository.CarRepository;
import com.example.parkinglot.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;


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


    @Transactional
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
        if(!userDto.getCars().isEmpty()){
            for (Car car: user.getCars()){
                car = carRepository.findById(car.getId()).orElseThrow();
                car.setUser(user);
                carRepository.save(car);
            }
        }
        userRepository.save(user);

    }
    @Transactional
    public void updateUser(UserDto userDto){

        User user = convertToUser(userDto);

        List<Car> cars = carRepository.findByUserId(userDto.getId());
        Set<Long> carIds = userDto.getCars().stream().map(CarDto::getId).collect(Collectors.toSet());
        for(Car car : cars){
            if(carIds.contains(car.getId())) {
                continue;
            }
            car.setUser(null);
            carRepository.save(car);
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        if(!user.getCars().isEmpty()){
            for(Car car : user.getCars()){
                car.setUser(null);
            }
        }
        userRepository.deleteById(id);
    }

    public UserDto convertToDto(User user){
        UserDto userDto= new UserDto();
        List<CarDto> listCarDtos = new ArrayList<>();
        Set<CarDto> carDtos = Collections.emptySet();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        if(!user.getCars().isEmpty()){
            for(Car car: user.getCars()){
                CarDto carDto = new CarDto();
                carDto.setId(car.getId());
                carDto.setPlateNumber(car.getPlateNumber());
                listCarDtos.add(carDto);
            }
            carDtos = new HashSet<>(listCarDtos);

        }
        return new UserDto(user.getId(), user.getName(), carDtos);

    }
    public User convertToUser(UserDto userDto){

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        if(!userDto.getCars().isEmpty()){
            user.setCars(new ArrayList<>());
            for (CarDto carDto : userDto.getCars()){
                Car car = carRepository.findById(carDto.getId()).orElseThrow();
                user.addCar(car);
                car.setUser(user);
            }
        }
        return user;
    }
}
