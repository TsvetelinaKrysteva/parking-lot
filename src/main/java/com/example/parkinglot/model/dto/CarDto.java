package com.example.parkinglot.model.dto;


import java.util.Objects;

public class CarDto {


    private Long id;
    private String plateNumber;
    private ParkingPlaceDto parkingPlaceDto;
    private ParkingZoneDto parkingZoneDot;


    private UserDto user;



    public CarDto() {
    }

    public CarDto(String plateNumber, ParkingPlaceDto parkingPlaceDto, ParkingZoneDto parkingZoneDto, UserDto user, Long id) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.parkingPlaceDto = parkingPlaceDto;
        this.parkingZoneDot = parkingZoneDto;
        this.user = user;

//        this.parkingPlaceNum = parkingPlace;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public ParkingPlaceDto getParkingPlaceDto() {
        return parkingPlaceDto;
    }

    public void setParkingPlaceDto(ParkingPlaceDto parkingPlaceDto) {
        this.parkingPlaceDto = parkingPlaceDto;
    }

    public ParkingZoneDto getParkingZoneDot() {
        return parkingZoneDot;
    }

    public void setParkingZoneDot(ParkingZoneDto parkingZoneDot) {
        this.parkingZoneDot = parkingZoneDot;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return Objects.equals(id, carDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return plateNumber;
    }
}
