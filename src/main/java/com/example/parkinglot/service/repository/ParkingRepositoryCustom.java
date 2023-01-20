package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.model.entity.Parking;

import java.util.List;

public interface ParkingRepositoryCustom {

    List<Parking> findByFilter(ParkingFilterDto filter);

}
