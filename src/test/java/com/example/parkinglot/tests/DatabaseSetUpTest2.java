package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


//To be used in TestSuit instead of DatabaseSetUpTest
@SpringBootTest(classes = ParkingLotApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseSetUpTest2 {

    @Autowired
    ParkingService parkingService;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingPlaceService parkingPlaceService;
    @Autowired
    CarService carService;



    @Test
    void createAll() {
        createParking();
        createZone();
        createPlace();
        createCar();
    }



    void createParking(){
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

    void createZone(){
        List<ParkingDto> parkings = parkingService.getParkings().stream()
                .sorted(Comparator.comparing(ParkingDto::getId))
                .collect(Collectors.toList());
        Assertions.assertEquals(2, parkings.size());

        ParkingZoneDto zone1 = new ParkingZoneDto();
        zone1.setParkingDto(parkings.get(0));
        zone1.setName("Zone 1");
        ParkingZoneDto zone2 = new ParkingZoneDto();
        zone2.setParkingDto(parkings.get(1));
        zone2.setName("Zone 2");
        parkingZoneService.createParkingZone(zone1);
        parkingZoneService.createParkingZone(zone2);

        Assertions.assertEquals(2, parkingZoneService.getParkingZones().size());

    }

    void createPlace(){
        List<ParkingZoneDto> zones = parkingZoneService.getParkingZones().stream()
                .sorted(Comparator.comparing(ParkingZoneDto::getId))
                .collect(Collectors.toList());
        ParkingPlaceDto place1 = new ParkingPlaceDto();
        place1.setNumber("1");
        place1.setParkingZone(zones.get(0));
        ParkingPlaceDto place2 = new ParkingPlaceDto();
        place2.setNumber("2");
        place2.setParkingZone(zones.get(1));

        parkingPlaceService.createParkingPlace(place1);
        parkingPlaceService.createParkingPlace(place2);

        Assertions.assertEquals(2, parkingPlaceService.getParkingPlaces().size());
    }

    void createCar(){
        List<ParkingPlaceDto> places = parkingPlaceService.getParkingPlaces().stream()
                .sorted(Comparator.comparing(ParkingPlaceDto::getId))
                .collect(Collectors.toList());
        CarDto car1 = new CarDto();
        car1.setPlateNumber("PB8888KA");
        CarDto car2 = new CarDto();
        car2.setPlateNumber("PA4444BX");
        car2.setParkingPlaceDto(places.get(0));

        carService.createCar(car1);
        carService.createCar(car2);

        Assertions.assertEquals(2, carService.getAllCars().size());


    }


}

