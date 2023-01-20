package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.model.entity.ParkingPlace;

import java.util.List;

public interface ParkingPLaceRepositoryCustom {
    List<ParkingPlace> findByFilter(ParkingPlaceFilterDto filter);
}
