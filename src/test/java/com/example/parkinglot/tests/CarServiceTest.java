package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
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
public class CarServiceTest {
    @Autowired
    CarService carService;
    @Autowired
    ParkingPlaceService parkingPlaceService;



    @Test
    @Order(10)
    public void testCreateCarPlateNumberException(){
        CarDto car = new CarDto();
        car.setPlateNumber("PB8888KA");

        Assertions.assertThrows(RuntimeException.class, () -> carService.createCar(car));
    }

    @Test
    @Order(20)
    public void testCreateCarOnTakenPLaceException(){
        CarDto car = new CarDto();
        car.setPlateNumber("CB6666BX");
        car.setParkingPlaceDto(parkingPlaceService.getParkingPlaceById(1L));
        Assertions.assertThrows(RuntimeException.class, () -> carService.createCar(car));
    }

    @Test
    @Order(30)
    void getCarById(){
        CarDto car = carService.getCarById(2L);
        Assertions.assertEquals("PA4444BX", car.getPlateNumber());
        Assertions.assertEquals("1", car.getParkingPlaceDto().getNumber());
        Assertions.assertEquals("Zone 1", car.getParkingZoneDot().getName());
    }

    @Test
    @Order(40)
    void testGetCarByPlaceId(){
        CarDto car = carService.getCarByPlaceId(1L);
        Assertions.assertEquals("PA4444BX", car.getPlateNumber());
    }

    @Test
    @Order(50)
    @Transactional
    void testUpdateCar(){
        CarDto car = new CarDto();
        car.setId(2L);
        car.setPlateNumber("updated");
        car.setParkingPlaceDto(parkingPlaceService.getParkingPlaceById(2L));

        carService.updateCar(car);
        CarDto carUpdated = carService.getCarById(2L);
        Assertions.assertEquals("updated", carUpdated.getPlateNumber());
        Assertions.assertEquals("2", carUpdated.getParkingPlaceDto().getNumber());
        Assertions.assertEquals("Zone 2", carUpdated.getParkingZoneDot().getName());
    }

    @Test
    @Order(60)
    @Transactional
    void testDeleteCar(){
        carService.deleteCar(2L);
        Assertions.assertEquals(1, carService.getAllCars().size());
    }

}
