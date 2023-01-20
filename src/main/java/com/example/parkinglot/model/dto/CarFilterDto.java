package com.example.parkinglot.model.dto;

public class CarFilterDto {

    private Long id;
    private String plateNumber;
    private int parkingPlaceNumber;
    private String parkingZoneName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
