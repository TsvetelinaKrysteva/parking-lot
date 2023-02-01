package com.example.parkinglot.tests;

import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;



@Suite
@SelectClasses({DatabaseSetUpTest.class, ParkingZoneServiceTest.class, ParkingPlaceServiceTest.class, CarServiceTest.class})

//@SpringBootTest(classes = ParkingLotApplication.class)


public class TestSuit {

    @Autowired
    ParkingService parkingService;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingPlaceService parkingPlaceService;
    @Autowired
    CarService carService;



    @BeforeAll
    void setUp(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> carService.getAllCars());
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingPlaceService.getParkingPlaces());
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingZoneService.getParkingZones());
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingService.getParkings());
    }

    @AfterAll
    void truncate(){
        carService.truncateCarTable();
        parkingPlaceService.truncateParkingPlaceTable();
        parkingZoneService.truncateParkingZoneTable();
        parkingService.truncateParkingTable();
    }
}
