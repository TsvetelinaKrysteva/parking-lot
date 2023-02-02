package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.UserFilterDto;
import com.example.parkinglot.model.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByFilter(UserFilterDto filter);
}
