package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByParkingPlaceId(Long parkingPlaceId);
    Optional<Car> findByPlateNumber(String plateNumber);

}
