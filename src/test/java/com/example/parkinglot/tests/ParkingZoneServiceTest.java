package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest(classes = ParkingLotApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParkingZoneServiceTest {

    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingService parkingService;



    @Test
    @Order(10)
    void testCreateParkingZoneException(){
        ParkingZoneDto zone = new ParkingZoneDto();
        zone.setParkingDto(parkingService.getParkingById(2L));
        zone.setName("Zone 1");
        Assertions.assertThrows(RuntimeException.class, () -> parkingZoneService.createParkingZone(zone));
    }

    @Test
    @Order(20)
    void testGetParkingZoneById(){
        ParkingZoneDto zone = parkingZoneService.getParkingZoneById(1L);
        Assertions.assertEquals("Zone 1", zone.getName());
        Assertions.assertEquals("Parking 1", zone.getParkingDto().getName());
        Assertions.assertEquals("1", zone.getPlaces().get(0).getNumber());
    }
    @Test
    @Order(30)
    void testGetZoneByIdException(){
        Assertions.assertThrows(RuntimeException.class, () -> parkingZoneService.getParkingZoneById(3L));
    }

    @Test
    @Order(40)
    void testGetZonesByParkingId(){
        List<ParkingZoneDto> zones = parkingZoneService.getZonesByParkingId(1L);
        Assertions.assertEquals(1,zones.size());
        Assertions.assertEquals("Zone 1", zones.get(0).getName());
    }



    @Test
    @Order(50)
    void getZoneByCarId(){
        ParkingZone zone = parkingZoneService.getZoneByCarId(2L);
        Assertions.assertEquals("Zone 1", zone.getName());
    }

    @Test
    @Order(60)
    @Transactional
    void testUpdateZone(){
        ParkingZoneDto zone = new ParkingZoneDto();
        zone.setId(1L);
        zone.setParkingDto(parkingService.getParkingById(2L));
        zone.setName("Zone update");
        parkingZoneService.updateParkingZone(zone);

        Assertions.assertEquals("Zone update", parkingZoneService.getParkingZoneById(1L).getName());
        Assertions.assertEquals("Parking 2", parkingZoneService.getParkingZoneById(1L).getParkingDto().getName());

    }

    @Test
    @Order(70)
    @Transactional
    void testDeleteZone(){
        parkingZoneService.deleteParkingZone(2L);
        Assertions.assertEquals(1, parkingZoneService.getParkingZones().size());
    }
}
