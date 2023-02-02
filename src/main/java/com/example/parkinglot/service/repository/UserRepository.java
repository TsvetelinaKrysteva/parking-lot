package com.example.parkinglot.service.repository;

import com.example.parkinglot.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.vaadin.artur.helpers.CrudService;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> , UserRepositoryCustom{
}
