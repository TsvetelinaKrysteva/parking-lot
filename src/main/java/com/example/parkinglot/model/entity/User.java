package com.example.parkinglot.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Car> car;


    public User() {
    }

    public User(String name, List<Car> car) {
        this.name = name;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }
    public void addCar(Car car){
        this.car.add(car);
    }
}
