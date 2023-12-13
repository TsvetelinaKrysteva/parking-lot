package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Long>, CarRepositoryCustom {

    Optional<Car> findByParkingPlaceId(Long parkingPlaceId);
    Optional<Car> findByPlateNumber(String plateNumber);
    Optional<Car> findByPlateNumberAndParkingPlaceId(String plateNumber, Long parkingPlaceId);

    @Query("select c from Car c "+
            "left join c.users u "+
            "where :userId = u.id")
    List<Car> findByUserId(Long userId);
}
