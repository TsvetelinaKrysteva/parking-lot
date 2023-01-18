package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.ParkingPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingPlaceRepository extends CrudRepository<ParkingPlace, Long> {

    Optional<ParkingPlace> findByCarId(Long carId);
    List<ParkingPlace> findByParkingZoneId(Long parkingZoneId);

}

