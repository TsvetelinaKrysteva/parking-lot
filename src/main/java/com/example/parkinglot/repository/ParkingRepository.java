package com.example.parkinglot.repository;

import com.example.parkinglot.model.entity.Parking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends CrudRepository<Parking, Long> {
}
