package com.example.parkinglot.model.dto;


import java.util.List;
import java.util.Objects;

public class CarDto {


    private Long id;
    private String plateNumber;
    private ParkingPlaceDto parkingPlaceDto;
    private ParkingZoneDto parkingZoneDot;


    private List<UserDto> users;



    public CarDto() {
    }

    public CarDto(String plateNumber, ParkingPlaceDto parkingPlaceDto, ParkingZoneDto parkingZoneDto, List<UserDto> users, Long id) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.parkingPlaceDto = parkingPlaceDto;
        this.parkingZoneDot = parkingZoneDto;
        this.users = users;

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
    public List<UserDto> getUsers() {
        return users;
    }

    public void setUser(List<UserDto> users) {
        this.users = users;
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
