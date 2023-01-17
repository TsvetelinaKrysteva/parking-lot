package com.example.parkinglot.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_places")
public class ParkingPlace extends BaseEntity {
    @Column(name = "number")
    private int number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parking_zone_id", nullable = false)
    private ParkingZone parkingZone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Car car;

    public ParkingPlace() {
    }

    public ParkingPlace(int number) {
        this.number = number;
    }

    public ParkingPlace(int number, ParkingZone parkingZone, Car car) {
        this.number = number;
        this.parkingZone = parkingZone;
        this.car = car;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ParkingZone getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(ParkingZone parkingZone) {
        this.parkingZone = parkingZone;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}

