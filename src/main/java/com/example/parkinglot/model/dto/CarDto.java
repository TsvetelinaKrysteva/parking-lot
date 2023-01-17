package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.ParkingPlace;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

public class CarDto {


    private Long id;
    private String plateNumber;
    private int parkingPlaceNumber;
    private String parkingZoneName;



    public CarDto() {
    }

    public CarDto(String plateNumber, int parkingPlaceNumber, String parkingZoneName, Long id) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.parkingPlaceNumber = parkingPlaceNumber;
        this.parkingZoneName = parkingZoneName;

//        this.parkingPlaceNum = parkingPlace;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getParkingPlaceNumber() {
        return parkingPlaceNumber;
    }

    public void setParkingPlaceNumber(int parkingPlaceNumber) {
        this.parkingPlaceNumber = parkingPlaceNumber;
    }

    public String getParkingZoneName() {
        return parkingZoneName;
    }

    public void setParkingZoneName(String parkingZoneName) {
        this.parkingZoneName = parkingZoneName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
