package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long>, CarRepositoryCustom {

    Optional<Car> findByParkingPlaceId(Long parkingPlaceId);
    Optional<Car> findByPlateNumber(String plateNumber);
    Optional<Car> findByPlateNumberAndParkingPlaceId(String plateNumber, Long parkingPlaceId);

    List<Car> findByUserId(Long userId);
}
