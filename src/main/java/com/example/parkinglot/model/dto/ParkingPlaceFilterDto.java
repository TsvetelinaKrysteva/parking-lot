package com.example.parkinglot.model.dto;

public class ParkingPlaceFilterDto {
    private Long id;

    private int number;

    private String parkingZoneName;

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
