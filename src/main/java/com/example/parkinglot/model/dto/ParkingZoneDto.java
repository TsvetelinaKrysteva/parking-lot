package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;


import java.util.List;
import java.util.stream.Collectors;

public class ParkingZoneDto {

    private Long id;

    private String name;
    private List<Integer> placeNames;

    public ParkingDto getParkingDto() {
        return parkingDto;
    }

    public void setParkingDto(ParkingDto parkingDto) {
        this.parkingDto = parkingDto;
    }

    private ParkingDto parkingDto;

    public ParkingZoneDto() {
    }

    public ParkingZoneDto(String name, List<ParkingPlace> parkingPlaces, Long id, ParkingDto parkingDto) {
        this.id = id;
        this.name = name;
        this.placeNames = parkingPlaces.stream().map(ParkingPlace::getNumber).collect(Collectors.toList());
        this.parkingDto = parkingDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPlaceNames() {
        return placeNames;
    }

    public void setPlaceNames(List<Integer> placeNames) {
        this.placeNames = placeNames;
    }

    public ParkingZoneDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
