package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.ParkingZone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingZoneRepository extends CrudRepository<ParkingZone, Long>, ParkingZoneRepositoryCustom{

    List<ParkingZone> findByParkingId(Long parkingId);
    Optional<ParkingZone> findByParkingPlacesId(Long parkingPlacesId);
    Optional<ParkingZone> findByName(String name);
}
