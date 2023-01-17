package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.Car;
import com.example.parkinglot.model.entity.ParkingZone;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class ParkingPlaceDto {

    private Long id;

    private int number;

    private ParkingZoneDto parkingZone;

    public ParkingPlaceDto() {
    }

    public ParkingPlaceDto(int number, ParkingZoneDto parkingZone, Long id) {
        this.id = id;
        this.number = number;
        this.parkingZone = parkingZone;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ParkingZoneDto getParkingZone() {
        return parkingZone;
    }

    public void setParkingZone(ParkingZoneDto parkingZone) {
        this.parkingZone = parkingZone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
