package com.example.parkinglot.model.dto;


public class ParkingPlaceDto {

    private Long id;

    private String number;

    private ParkingZoneDto parkingZone;

    public ParkingPlaceDto() {
    }

    public ParkingPlaceDto(String number, ParkingZoneDto parkingZone, Long id) {
        this.id = id;
        this.number = number;
        this.parkingZone = parkingZone;

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
