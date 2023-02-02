package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.Car;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

public class UserDto {
    private Long id;
    private String name;

    private Set<CarDto> car;


    public UserDto() {
    }

    public UserDto(Long id, String name, Set<CarDto> car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CarDto> getCar() {
        return car;
    }

    public void setCar(Set<CarDto> car) {
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
