package com.example.parkinglot.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String name;

//    @ManyToMany(mappedBy = "user")
@ManyToMany
@JoinTable(name = "car_user",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "car_id")
)
    private List<Car> cars;


    public User() {
    }

    public User(String name, List<Car> cars) {
        this.name = name;
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> car) {
        this.cars = car;
    }
    public void addCar(Car car){
        this.cars.add(car);
    }
}
