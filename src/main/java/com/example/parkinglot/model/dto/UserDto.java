package com.example.parkinglot.model.dto;

import java.util.Objects;
import java.util.Set;

public class UserDto {
    private Long id;
    private String name;

    private Set<CarDto> cars;


    public UserDto() {
    }

    public UserDto(Long id, String name, Set<CarDto> car) {
        this.id = id;
        this.name = name;
        this.cars = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CarDto> getCars() {
        return cars;
    }

    public void setCars(Set<CarDto> cars) {
        this.cars = cars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserDto userDto = (UserDto) obj;
        return Objects.equals(id, userDto.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
