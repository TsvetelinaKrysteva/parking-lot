package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.Parking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends CrudRepository<Parking, Long>, ParkingRepositoryCustom {
    Optional<Parking> findByZonesId(Long zonesId);
    Optional<Parking> findByName(String name);

}
