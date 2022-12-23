package com.example.parkinglot.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Car extends BaseEntity {
    @Column(unique = true)
    private String plateNumber;
    @JsonBackReference
    @OneToOne
    private ParkingPlace parkingPlace;


    public Car() {

    }

    public Car(String plateNumber, ParkingPlace parkingPlace) {
        this.plateNumber = plateNumber;
        this.parkingPlace = parkingPlace;
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

}
