package com.example.parkinglot.tests;


import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import com.example.parkinglot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = ParkingLotApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseSetUpTest {
    @Autowired
    ParkingService parkingService;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingPlaceService parkingPlaceService;
    @Autowired
    CarService carService;
    @Autowired
    UserService userService;

    @Test
    @Order(10)
    void testCreateParking(){
        ParkingDto parking1 = new ParkingDto();
        parking1.setName("Parking 1");
        parking1.setStreet("Vasil Aprilov");
        parking1.setCity("Plovdiv");
        parking1.setZipCode("4000");
        ParkingDto parking2 = new ParkingDto();
        parking2.setName("Parking 2");
        parking2.setStreet("Stoyan Angelov");
        parking2.setCity("Sofia");
        parkingService.createParking(parking1);
        parkingService.createParking(parking2);

        Assertions.assertEquals(2, parkingService.getParkings().size());

    }


    @Test
    @Order(20)
    void testCreateZone(){
        ParkingZoneDto zone1 = new ParkingZoneDto();
        zone1.setParkingDto(parkingService.getParkingById(1L));
        zone1.setName("Zone 1");
        ParkingZoneDto zone2 = new ParkingZoneDto();
        zone2.setParkingDto(parkingService.getParkingById(2L));
        zone2.setName("Zone 2");
        parkingZoneService.createParkingZone(zone1);
        parkingZoneService.createParkingZone(zone2);

        Assertions.assertEquals(2, parkingZoneService.getParkingZones().size());

    }

    @Test
    @Order(30)
    void testCreatePlace(){
        ParkingPlaceDto place1 = new ParkingPlaceDto();
        place1.setNumber("1");
        place1.setParkingZone(parkingZoneService.getParkingZoneById(1L));
        ParkingPlaceDto place2 = new ParkingPlaceDto();
        place2.setNumber("2");
        place2.setParkingZone(parkingZoneService.getParkingZoneById(2L));

        parkingPlaceService.createParkingPlace(place1);
        parkingPlaceService.createParkingPlace(place2);

        Assertions.assertEquals(2, parkingPlaceService.getParkingPlaces().size());
    }


    @Test
    @Order(40)
    void testCreateCar(){

        CarDto car1 = new CarDto();
        car1.setPlateNumber("PB8888KA");
        CarDto car2 = new CarDto();
        car2.setPlateNumber("PA4444BX");
        car2.setParkingPlaceDto(parkingPlaceService.getParkingPlaceById(1L));

        carService.createCar(car1);
        carService.createCar(car2);

        Assertions.assertEquals(2, carService.getAllCars().size());


    }
    @Test
    @Order(50)
    void testCreateUser(){
        UserDto userDto1 = new UserDto();
        userDto1.setName("Ivan Ivanov");
        userDto1.setCars(new HashSet<>());
        UserDto userDto2 = new UserDto();
        userDto2.setName("Georgi Georgiev");
        Set<CarDto> cars = Set.of(carService.getCarById(1L), carService.getCarById(2L));
        userDto2.setCars(cars);
        userService.createUser(userDto1);
        userService.createUser(userDto2);

        Assertions.assertEquals(2, userService.getAllUsers().size());

    }

}
