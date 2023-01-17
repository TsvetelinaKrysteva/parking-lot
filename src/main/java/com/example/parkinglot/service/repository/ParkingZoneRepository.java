package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingZoneRepository extends CrudRepository<ParkingZone, Long> {
}
