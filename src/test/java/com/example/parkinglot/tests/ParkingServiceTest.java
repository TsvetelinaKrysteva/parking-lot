package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.service.ParkingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = ParkingLotApplication.class)
public class ParkingServiceTest {
    @Autowired
    private ParkingService parkingService;



    @Test
    @Order(10)
    void testCreateParkingException(){
        ParkingDto parking = new ParkingDto();
        parking.setName("Parking 1");
        parking.setStreet("Ivan Vazov");
        parking.setCity("Stara Zagora");
        Assertions.assertThrows(RuntimeException.class, () -> parkingService.createParking(parking));
    }

    @Test
    @Order(20)
    void testGetParkingByCarId(){

        Parking parking = parkingService.getParkingByCarId(2L);
        Assertions.assertEquals("Parking 1", parking.getName());
    }

    @Test
    void testGetParkingByCarIdException(){
        Assertions.assertThrows(RuntimeException.class, () -> parkingService.getParkingByCarId(1L));
    }

    @Test
    @Order(30)
    void testGetParkingById(){
        ParkingDto parking = parkingService.getParkingById(2L);
        Assertions.assertEquals("Parking 2", parking.getName());
        Assertions.assertEquals("Sofia", parking.getCity());
        Assertions.assertEquals("Stoyan Angelov", parking.getStreet());


    }

    @Test
    @Order(40)
    @Transactional
    void testUpdateParking(){
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setName("Updated parking");
        parkingService.updateParking(parkingDto, 2L);
        Assertions.assertEquals("Updated parking", parkingService.getParking(2L).getName());
    }

    @Test
    @Order(50)
    @Transactional
    void testDeleteParking(){
        parkingService.deleteParking(2L);
        Assertions.assertEquals(1, parkingService.getParkings().size());
    }
}
