package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingZoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest(classes = ParkingLotApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParkingPlaceServiceTest {
    @Autowired
    private ParkingPlaceService parkingPlaceService;
    @Autowired
    private ParkingZoneService parkingZoneService;




    @Test
    @Order(10)
    void testGetParkingPLaceById(){
        ParkingPlaceDto place = parkingPlaceService.getParkingPlaceById(1L);
        Assertions.assertEquals("1", place.getNumber());
        Assertions.assertEquals("Zone 1", place.getParkingZone().getName());
        Assertions.assertEquals("PA4444BX", place.getCar().getPlateNumber());
    }

    @Test
    @Order(20)
    void testFindByCarId(){
        Assertions.assertEquals("1", parkingPlaceService.findByCarId(2L).getNumber());
    }

    @Test
    @Order(30)
    void testGetParkingPlacesByZoneId(){
        Assertions.assertEquals(1, parkingPlaceService.getParkingPLacesByZoneId(1L).size());
    }

    @Test
    @Order(40)
    @Transactional
    void testUpdateParkingPlace(){
        ParkingPlaceDto parkingPlaceDto = new ParkingPlaceDto();
        parkingPlaceDto.setId(1L);
        parkingPlaceDto.setNumber("10");
        parkingPlaceDto.setParkingZone(parkingZoneService.getParkingZoneById(1L));
        parkingPlaceService.updateParkingPlace(parkingPlaceDto);
        Assertions.assertEquals("10", parkingPlaceService.getParkingPlaceById(1L).getNumber());
    }



    //Deletes only places that are not connected to a car

    @Test
    @Order(50)
    @Transactional
    void testDeletePlace(){
        parkingPlaceService.deleteParkingPlace(2L);
        Assertions.assertEquals(1, parkingPlaceService.getParkingPlaces().size());
    }



}
