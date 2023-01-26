package com.example.parkinglot.model.dto;


public class CarDto {


    private Long id;
    private String plateNumber;
    private ParkingPlaceDto parkingPlaceDto;
    private ParkingZoneDto parkingZoneDot;



    public CarDto() {
    }

    public CarDto(String plateNumber, ParkingPlaceDto parkingPlaceDto, ParkingZoneDto parkingZoneDto, Long id) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.parkingPlaceDto = parkingPlaceDto;
        this.parkingZoneDot = parkingZoneDto;

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


}
