package com.example.parkinglot.model.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_places")
public class ParkingPlace extends BaseEntity {
    @NotNull
    @Column(name = "number")
    private int number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parking_zone_id")
    private ParkingZone parkingZone;

    @OneToOne(mappedBy = "parkingPlace")
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

