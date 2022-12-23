package com.example.parkinglot.repository;

import com.example.parkinglot.model.entity.ParkingPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends CrudRepository<ParkingPlace, Long> {

}

