package com.example.parkinglot.tests;

import com.example.parkinglot.ParkingLotApplication;
import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = ParkingLotApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    CarService carService;

    @Test
    @Order(10)
    void testGetUserById(){
        UserDto user = userService.getUserById(2L);
        Assertions.assertEquals("Georgi Georgiev", user.getName());
        Set<CarDto> cars = user.getCars();
        Assertions.assertEquals(2, cars.size());

    }
    @Test
    @Order(20)
    void testGetUserByIdException(){
        Assertions.assertThrows(RuntimeException.class, () -> userService.getUserById(3L));
    }

//    @Transactional
//    @Test
//    @Order(30)
//    void testUpdateUser(){
////        UserDto user = new UserDto();
////        user.setId(2L);
////        user.setName("Milen");
////        user.setCars(new HashSet<>());
////        userService.updateUser(user);
////        UserDto userAfterUpdate = userService.getUserById(2L);
////        Assertions.assertEquals("Milen", userAfterUpdate.getName());
////        Assertions.assertEquals(0, userAfterUpdate.getCars().size());
//
//        UserDto user = new UserDto();
//        user.setId(1L);
//        user.setName("Milen");
//        user.setCars(new HashSet<>());
//        userService.updateUser(user);
//        UserDto userAfterUpdate = userService.getUserById(1L);
//        Assertions.assertEquals("Milen", userAfterUpdate.getName());
//        Assertions.assertEquals(0, userAfterUpdate.getCars().size());
//
//    }

    @Transactional
    @Test
    @Order(40)
    void testDeleteUser(){
        userService.deleteUser(1L);
        Assertions.assertThrows(RuntimeException.class,() -> userService.getUserById(1L));
    }

}
