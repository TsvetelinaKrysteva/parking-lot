package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.ParkingZone;

import java.util.List;

public interface ParkingZoneRepositoryCustom {
    List<ParkingZone> findByFilter(ParkingZoneFilterDto filter);

}
