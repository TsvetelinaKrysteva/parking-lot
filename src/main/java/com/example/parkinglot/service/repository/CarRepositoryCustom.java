package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.entity.Car;

import java.util.List;

public interface CarRepositoryCustom {
    List<Car> findByFilter(CarFilterDto filter);
}
