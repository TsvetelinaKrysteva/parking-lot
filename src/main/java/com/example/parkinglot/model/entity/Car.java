package com.example.parkinglot.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "cars")
public class Car extends BaseEntity {


    @NotNull
    @Column(unique = true)
    private String plateNumber;

    @OneToOne
    @JoinColumn(name = "parking_place_id")
    private ParkingPlace parkingPlace;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Car() {

    }

    public Car(String plateNumber, ParkingPlace parkingPlace, User user) {
        this.plateNumber = plateNumber;
        this.parkingPlace = parkingPlace;
        this.user = user;
    }


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
